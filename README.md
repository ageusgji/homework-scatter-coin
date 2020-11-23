# 뿌리기 기능 구현
## Install  

> ```$ docker-compose up```

## Only App Install    
> build : ```$ docker build -t coinscatterapp . ```  
> run :  ```$ docker run coinscatterapp ```  
## Swagger
- URI : 
  ```http://localhost:8888/swagger-ui.html```   

## APIs 
- 뿌리기   
  * URI : 
```http://localhost:8888/scatter``` 
  + Parameter  

  | Parameter  | Description | Parameter Type | Data Type |  
  | :---  | :--- | :---: | :---: |  
  |  X-USER-ID | User ID | header | Long |    
  |  X-ROOM-ID | Room ID | header | string |    
  | headCount   | 뿌릴 인원 | query | Long |  
  | coins   | 뿌릴 금액 | query | Long |  
  | token   | 토큰  | query | string(Length 3) |    
  * Example : 
```http://localhost:8888/scatter?headCount=121&coins=84748``` 

- 받기  
  * URI : 
```http://localhost:8888/scatter``` 

  * Parameter  

  | Parameter  | Description | Parameter Type | Data Type |  
  | :---  | :--- | :---: | :---: |  
  |  X-USER-ID | User ID | header | Long |    
  |  X-ROOM-ID | Room ID | header | string |    
  | token   | 토큰  | query | string(Length 3) |  
  
  * Example : 
```http://localhost:8888/receive?token=NtV```

- 조회  
  * URI : 
```http://localhost:8888/scatter``` 

  * Parameter  

  | Parameter  | Description | Parameter Type | Data Type |  
  | :---  | :--- | :---: | :---: |  
  |  X-USER-ID | User ID | header | Long |    
  |  X-ROOM-ID | Room ID | header | string |    
  | token   | 토큰  | query | string(Length 3) |  
  
  * Example : 
```http://localhost:8888/display?token=NtV```


  

  
---
contact : ageusgji@gmail.com

