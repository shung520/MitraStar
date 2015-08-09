#!/bin/sh

ffmpeg -ss 00:00:00.000 -i model.ts -t 01:00:00.000 -c:v copy -c:a copy out.ts
ffmpeg -i out.ts -acodec copy -vcodec copy out.mp4

for var in {0..11}
do
	let t=$[var*5]
	ffmpeg -ss 00:$t:00.000 -i out.mp4 -t 00:05:00.000 -c:v copy -c:a copy out$var.mp4
done