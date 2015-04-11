cd server

last_pid=0

if [ -f .server_pid ]; then
	last_pid=`cat .server_pid`
fi

if [ $last_pid -ne 0 ]; then
	kill $last_pid
fi

java Server &

echo $! > .server_pid
