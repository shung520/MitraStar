#!/bin/sh

# define variable
video_filename=$1
temp_ts_filename=temp_out.ts
temp_mp4_filename=temp_out.mp4
split_video_to_minuts=5

# get video duration
video_duration=$(ffmpeg -i $video_filename 2>&1 | grep "Duration"| cut -d ' ' -f 4 | sed s/,//)

# convert ts to mp4
ffmpeg -y -ss 00:00:00.00 -i $video_filename -t $video_duration -c:v copy -c:a copy $temp_ts_filename
ffmpeg -y -i $temp_ts_filename -acodec copy -vcodec copy $temp_mp4_filename

# # split video
for i in $(seq 0 12)
do
	start_time=$[i*split_video_to_minuts*60]
	ffmpeg -y -ss $start_time -i $temp_mp4_filename -t $[split_video_to_minuts*60] -c:v copy -c:a copy out_$i.mp4
done