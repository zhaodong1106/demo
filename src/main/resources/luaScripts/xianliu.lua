--
-- Created by IntelliJ IDEA.
-- User: T011689
-- Date: 2018/9/27
-- Time: 17:19
-- To change this template use File | Settings | File Templates.
local userSign=redis.call('get','sign:'..KEYS[1]);
local count=tonumber(ARGV[1]);
redis.log(redis.LOG_WARNING,userSign);
redis.log(redis.LOG_WARNING,count);
if userSign then
    redis.log(redis.LOG_WARNING,'userSign cunzai');
else
    redis.log(redis.LOG_WARNING,'userSign no cunzai');
end
if userSign == 2 then
    redis.log(redis.LOG_WARNING,'userSign == count equals');
else
    redis.log(redis.LOG_WARNING,'userSign == count no equals')
end
if userSign and tonumber(userSign)==count then
    return 400;
else
    local now=redis.call('incr','sign:'..KEYS[1]);
    return now;
end

