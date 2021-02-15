#!/bin/bash

PARENTS=(tSNE_UNIFEI_2017 tSNE_UNIFEI_2018)
FOLDERS=(CHI2 FD MI)
SUBFOLDERS=(8 16 32 64 128 256 512 1024)

# resolutions
R720P='1280,720'
R1080P='1920,1080'
R2160P='3840,2160'
R4320P='7680,4320'
RLATEX='700,400'

# default colors
BLACK='#f0000000'
GREEN='#f000ff00'
RED='#f0ff0000'
FUCHSIA='#f0ff00ff'
YELLOW='#f0ffff00'
BLUE='#f00000ff'
ORANGE='#f0ffa500'

# color brewer colors
CBRED='#f0fb8072'
CBDARKRED='#f0941305'
CBLIGHTBLUE='#abd9e9'
CBBLUE='#f080b1d3'
CBDARKBLUE='#f0275372'
CBLIGHTORANGE='#fdae61'
CBORANGE='#e66101'
CBDARKORANGE='#d95f02'
CBLIGHTPURPLE='#b2abd2'
CBPURPLE='#5e3c99'
CBDARKPURPLE='#7570b3'
CBLIGHTLEMON='#b8e186'
CBLEMON='#4dac26'
CBLIGHTPINK='#f1b6da'
CBPINK='#d01c8b'

for PARENT in "${PARENTS[@]}"
do
    for FOLDER in "${FOLDERS[@]}"
    do
        for SUBFOLDER in "${SUBFOLDERS[@]}"
        do
            inputHam="./$PARENT/$FOLDER/$SUBFOLDER/ham.data"
            inputSpam="./$PARENT/$FOLDER/$SUBFOLDER/spam.data"
            inputDuped="./$PARENT/$FOLDER/$SUBFOLDER/duped.data"
            output="./$PARENT/UNIFEI_2017-$FOLDER-$SUBFOLDER.png"
            gnuplot -e " \
            set term postscript eps color fontfile './sfss1200.pfb' 'SFRM1200'; \
            set tics font 'SFRM1200,16'; \
            set key font 'SFRM1200,20'; \
            set terminal png size $R1080P; \
            set output '$output'; \
            set style line 1 lc rgb '$CBBLUE' pt 1 ps 2; \
            set style line 2 lc rgb '$CBRED' pt 2 ps 2; \
            set style line 3 lc rgb '$BLACK' pt 3 ps 2; \
            plot '$inputHam' using 1:2 with points ls 1 title 'Ham', \
                '$inputSpam' using 1:2 with points ls 2 title 'Spam', \
                '$inputDuped' using 1:2 with points ls 3 title 'Both'"
        done
    done
done

#zip 720p-green-red.zip *.png
#zip temp.zip *.png
#rm *.png
