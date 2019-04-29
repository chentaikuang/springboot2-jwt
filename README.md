# springboot2-jwt

一、启动服务

二、使用postman工具完成登录，获取到token
POST http://localhost:8080/user/login
参数：{"name":"ctk","psw":"123456"}

返回：
{
    "code": "200",
    "msg": "ok",
    "data":      "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjdGsiLCJyb2xlcyI6Imd1ZXN0IiwiaWF0IjoxNTU2NTE3NzQwLCJleHAiOjE1NTY2MDQxNDV9.uvDg8S6RyNS94Xe8I3UDUKHBAjWDA5secEasu6Lx1uY"
}

三、带上token访问受限资源controller，成功获取数据
POST http://localhost:8080/resource/data
请求header:
key:req_token
value:eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjdGsiLCJyb2xlcyI6Imd1ZXN0IiwiaWF0IjoxNTU2NTE3NzQwLCJleHAiOjE1NTY2MDQxNDV9.uvDg8S6RyNS94Xe8I3UDUKHBAjWDA5secEasu6Lx1uY

返回：
{
    "code": "200",
    "msg": "welcome",
    "data": "ctk"
}
