-- zadd_lua.lua
local key = KEYS[1]
local score = tonumber(ARGV[1])
local member = ARGV[2]
redis.call('ZADD', key, score, member)
return true