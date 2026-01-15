package com.ixuea.courses.mymusic.manager.impl;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.ixuea.courses.mymusic.component.api.HttpObserver;
import com.ixuea.courses.mymusic.component.lyric.parser.LyricParser;
import com.ixuea.courses.mymusic.component.song.model.Song;
import com.ixuea.courses.mymusic.manager.MusicPlayerListener;
import com.ixuea.courses.mymusic.manager.MusicPlayerManager;
import com.ixuea.courses.mymusic.manager.SuperAudioManager;
import com.ixuea.courses.mymusic.model.response.DetailResponse;
import com.ixuea.courses.mymusic.repository.DefaultRepository;
import com.ixuea.courses.mymusic.util.ListUtil;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import timber.log.Timber;

/**
 * 播放管理器默认实现
 */
public class MusicPlayerManagerImpl implements MusicPlayerManager, MediaPlayer.OnCompletionListener, AudioManager.OnAudioFocusChangeListener {
    private static final long DEFAULT_PUBLISH_MUSIC_PROGRESS_TIME = 16;
    private static MusicPlayerManagerImpl instance;
    private final Context context;
    private final MediaPlayer player;
    private final SuperAudioManager superAudioManager;
    private String uri;
    private Song data;
    private final Object focusLock = new Object();

    private static final int MESSAGE_PROGRESS = 0;
    /**
     * 创建Handler
     * 用来将事件转换到主线程
     */
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_PROGRESS:
                    //播放进度回调

                    //将进度设置到音乐对象
                    data.setProgress(player.getCurrentPosition());

                    //回调监听
                    ListUtil.eachListener(listeners, musicPlayerListener -> musicPlayerListener.onProgress(data));
                    break;
            }
        }
    };

    /**
     * 播放器监听器
     */
    private CopyOnWriteArrayList<MusicPlayerListener> listeners = new CopyOnWriteArrayList<>();
    private TimerTask timerTask;
    private Timer timer;

    /**
     * 音频焦点获取到了，继续播放
     */
    private boolean resumeOnFocusGain;

    /**
     * 是否准备播放了，也就是是否调用了prepare方法
     */
    private boolean isPrepare;

    /**
     * 私有构造方法
     * <p>
     * 这里外部就不能通过new方法来创建对象了
     *
     * @param context
     */
    private MusicPlayerManagerImpl(Context context) {
        //保存context
        //因为后面可能用到
        this.context = context.getApplicationContext();

        //音频管理器
        superAudioManager = SuperAudioManager.getInstance(this.context);

        //初始化播放器
        player = new MediaPlayer();

        //设置播放器准备完毕完毕监听器
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //将总进度保存到音乐对象
                data.setDuration(mp.getDuration());

                ListUtil.eachListener(listeners, musicPlayerListener -> musicPlayerListener.onPrepared(mp, data));
            }
        });

        //设置播放完毕监听器
        player.setOnCompletionListener(this);
    }

    /**
     * 获取播放管理器
     * getInstance：方法名可以随便取
     * 只是在Java这边大部分项目都取这个名字
     *
     * @return
     */
    public synchronized static MusicPlayerManager getInstance(Context context) {
        if (instance == null) {
            instance = new MusicPlayerManagerImpl(context);
        }
        return instance;
    }

    @Override
    public void play(String uri, Song data) {
        //保存信息
        this.uri = uri;
        this.data = data;

        //释放播放器
        player.reset();

        //获取音频焦点
        if (!requestAudioFocus()) {
            return;
        }

        playNow();
    }

    private void playNow() {
        isPrepare = true;

        try {
            if (uri.startsWith("content://")) {
                //内容提供者格式

                //本地音乐
                //uri示例：content://media/external/audio/media/23
                player.setDataSource(context, Uri.parse(uri));
            } else {
                //设置数据源
                player.setDataSource(uri);
            }

            //同步准备
            //真实项目中可能会使用异步
            //因为如果网络不好
            //同步可能会卡住
            player.prepare();
//            player.prepareAsync();

            //开始播放器
            player.start();

            //回调监听器
            publishPlayingStatus();

            //启动播放进度通知
            startPublishProgress();

            prepareLyric(data);
        } catch (IOException e) {
            //TODO 播放错误处理
        }

    }

    public void prepareLyric(Song data) {
        this.data = data;

        //歌词处理
        //真实项目可能会
        //将歌词这个部分拆分到其他组件中
        if (data.getParsedLyric() != null) {
            //通知歌词改变了
            onLyricReady();
        } else if (StringUtils.isNotBlank(data.getLyric())) {
            parseLyric();

            onLyricReady();
        } else {
            if (data.isLocal()) {
                onLyricReady();
            } else {
                DefaultRepository.getInstance()
                        .songDetail(data.getId())
                        .subscribe(new HttpObserver<DetailResponse<Song>>() {
                            @Override
                            public void onSucceeded(DetailResponse<Song> songDetailResponse) {
                                //请求成功
                                if (songDetailResponse != null && songDetailResponse.getData() != null) {
                                    //数据设置歌曲对象
                                    data.setStyle(songDetailResponse.getData().getStyle());
                                    data.setLyric(songDetailResponse.getData().getLyric());

                                    if (StringUtils.isNotBlank(data.getLyric())) {
                                        parseLyric();
                                    }
                                }

                                //通知歌词改变了
                                onLyricReady();
                            }
                        });
            }
        }
    }

    private void parseLyric() {
        data.setParsedLyric(LyricParser.parse(data.getStyle(), data.getLyric()));
    }

    private void onLyricReady() {
        //不管有没有歌词都要回调
        ListUtil.eachListener(listeners, listener -> listener.onLyricReady(data));
    }

    /**
     * 获取音频焦点
     *
     * @return true：获取成功
     */
    private boolean requestAudioFocus() {
        int audioFocusResult = superAudioManager.requestAudioFocus(this, AudioAttributes.CONTENT_TYPE_MUSIC);
        synchronized (focusLock) {
            if (audioFocusResult != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                //获取失败了，或者稍后才能获取到（拨打电话了）
                resumeOnFocusGain = true;
                return false;
            }
        }
        return true;
    }

    /**
     * 启动播放进度通知
     */
    private void startPublishProgress() {
        if (isEmptyListeners()) {
            //没有进度回调就不启动
            return;
        }

        if (!isPlaying()) {
            //没有播放音乐就不用启动
            return;
        }

        if (timerTask != null) {
            //已经启动了
            return;
        }

        timerTask = new TimerTask() {

            @Override
            public void run() {
                //如果没有监听器了就停止定时器
                if (isEmptyListeners()) {
                    stopPublishProgress();
                    return;
                }

                //这里是子线程
                //不能直接操作UI
                //为了方便外部
                //在内部就切换到主线程
                handler.obtainMessage(MESSAGE_PROGRESS).sendToTarget();
            }
        };

        //创建一个定时器
        timer = new Timer();

        //启动一个持续的任务

        //16毫秒
        //为什么是16毫秒？
        //因为后面我们要实现卡拉OK歌词
        //为了画面的连贯性
        //应该保持1秒60帧
        //所以1/60；就是一帧时间
        //如果没有卡拉OK歌词
        //那么每秒钟刷新一次即可
        timer.schedule(timerTask, 0, DEFAULT_PUBLISH_MUSIC_PROGRESS_TIME);
    }

    /**
     * 是否没有进度监听器
     *
     * @return
     */
    private boolean isEmptyListeners() {
        return listeners.size() == 0;
    }

    /**
     * 停止播放进度通知
     */
    private void stopPublishProgress() {
        //停止定时器任务
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }

        //停止定时器
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public boolean isPlaying() {
        return player.isPlaying();
    }

    @Override
    public void pause() {
        if (isPlaying()) {
            //如果在播放就暂停
            player.pause();

            ListUtil.eachListener(listeners, musicPlayerListener -> musicPlayerListener.onPaused(data));

            stopPublishProgress();
        }
    }

    @Override
    public void resume() {
        if (!isPlaying()) {
            //获取音频焦点
            if (!requestAudioFocus()) {
                return;
            }

            resumeNow();
        }
    }

    private void resumeNow() {
        //如果没有播放就播放
        player.start();

        //回调监听器
        publishPlayingStatus();

        //启动进度通知
        startPublishProgress();
    }

    @Override
    public void addMusicPlayerListener(MusicPlayerListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }

        //启动进度通知
        startPublishProgress();
    }

    @Override
    public void removeMusicPlayerListener(MusicPlayerListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void seekTo(int progress) {
        player.seekTo(progress);
    }

    /**
     * 发布播放中状态
     */
    private void publishPlayingStatus() {
//        for (MusicPlayerListener listener : listeners) {
//            listener.onPlaying(data);
//        }

        //使用重构后的方法
        ListUtil.eachListener(listeners, musicPlayerListener -> musicPlayerListener.onPlaying(data));
    }

    /**
     * 播放完毕了回调
     *
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        isPrepare = false;

        //回调监听器
        ListUtil.eachListener(listeners, listener -> listener.onCompletion(mp));
    }

    @Override
    public void setLooping(boolean looping) {
        player.setLooping(looping);
    }

    /**
     * 音频焦点改变了回调
     *
     * @param focusChange
     */
    @Override
    public void onAudioFocusChange(int focusChange) {
        Timber.d("onAudioFocusChange %s", focusChange);

        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                //获取到焦点了
                if (resumeOnFocusGain) {
                    if (isPrepare) {
                        resumeNow();
                    } else {
                        playNow();
                    }

                    resumeOnFocusGain = false;
                }
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                //永久失去焦点，例如：其他应用请求时，也是播放音乐
                if (isPlaying()) {
                    pause();
                }
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                //暂时性失去焦点，例如：通话了，或者呼叫了语音助手等请求
                if (isPlaying()) {
                    resumeOnFocusGain = true;
                    pause();
                }
                break;
        }
    }
}
