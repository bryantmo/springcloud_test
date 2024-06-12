-- zset-get-del.lua
local key = KEYS[1]
local start = tonumber(ARGV[1])
local stop = tonumber(ARGV[2]) or -1
local data = redis.call('ZRANGE', key, start, stop, 'WITHSCORES');
return data