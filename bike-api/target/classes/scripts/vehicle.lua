-- vehicle.lua local
local hexists0 = redis.call('HEXISTS', KEYS[1], ARGV[1])
if hexists0 == 1 then
	local tid = redis.call('HGET', KEYS[1], ARGV[1])
	local hexists1 = redis.call('HEXISTS', KEYS[2], tid)
	if hexists1 == 1 then
		local vid = redis.call('HGET', KEYS[2], tid)
		local hexists2 = redis.call('HEXISTS', KEYS[3], vid)
		if hexists2 == 1 then
			return redis.call('HGET', KEYS[3], vid)
		end
	end
end
return nil

