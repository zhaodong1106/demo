--
-- Created by IntelliJ IDEA.
-- User: T011689
-- Date: 2018/9/27
-- Time: 10:25
-- To change this template use File | Settings | File Templates.
--
local exist=redis.call('exists',"orderNo:"..KEYS[1]);
if exist==1 then
    return redis.call('incr',"orderNo:"..KEYS[1]);
else
    redis.call('set',"orderNo:"..KEYS[1],1);
    return 1;
end

