#! /bin/sh
# Author: Momen

echo -n "scullmodules: Number of args: "
echo $#

if [ $# -ne 1 ]; then
    echo "scullmodules: Error, Arguments are not equal 1!"
    exit 1
fi

input_cmd=$1

cd /lib/modules/5.15.124-yocto-standard/extra

case $input_cmd in
    start)
        echo "Loading misc-modules"
        start-stop-daemon -S -n init -a /usr/bin/module_load -- faulty
        modprobe hello
    ;;

    stop)
        echo "Unloading misc-modules modules"
        start-stop-daemon -K -n init
        start-stop-daemon -S -n init -a /usr/bin/module_unload -- faulty
        rmmod hello
    ;;

    *)
        echo "Error, input cmd is neither start nor stop"
    exit 1

esac
