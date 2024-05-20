## Efficient Donwload Manager (EDM)
~~~ 
하나의 LAN(로컬 환경)에 연결된 다수의 PC들이 web의 동일한 리소스를 다운로드, 스트리밍 할때 낭비되는 WAN 대역폭에 초점을 맞추는 소프트웨어 입니다. 
본 소프트웨어를 사용하여 파일을 다운로드 혹은 스트리밍 받을 때, 로컬 환경의 공유기의 성능만 받쳐준다면, 최적의 다운로드 속도를 낼 수 있습니다.
~~~
ex) 강의실에서 다수의 학생들이 하나의 파일을 다운로드 받는 상황

---

## 이론 개요
![파일 스트리밍 다운로드 001](https://github.com/huhdy32/edmservice/assets/101308287/a8b0cbde-0096-4cfc-99e0-7d6caad72b05)
![파일 스트리밍 다운로드 002](https://github.com/huhdy32/edmservice/assets/101308287/51a87abd-625f-47a8-b4c0-a26f6b2ca246)
![파일 스트리밍 다운로드 003](https://github.com/huhdy32/edmservice/assets/101308287/f4c4d9e2-8f14-4088-989b-2d2a5564d855)
![파일 스트리밍 다운로드 004](https://github.com/huhdy32/edmservice/assets/101308287/8645a147-a867-45d8-818f-99dfbe2fd2a3)
![파일 스트리밍 다운로드 005](https://github.com/huhdy32/edmservice/assets/101308287/f838ef80-7448-4684-aee8-525da3c0643d)
![파일 스트리밍 다운로드 006](https://github.com/huhdy32/edmservice/assets/101308287/ce5968a9-d840-46f5-8322-f182bf7b914e)
![파일 스트리밍 다운로드 007](https://github.com/huhdy32/edmservice/assets/101308287/d8ff41f4-4a8f-4696-8cd0-d64303940598)
![파일 스트리밍 다운로드 008](https://github.com/huhdy32/edmservice/assets/101308287/88bfd1a2-33f8-47aa-b982-733849a60809)
![파일 스트리밍 다운로드 009](https://github.com/huhdy32/edmservice/assets/101308287/82e21b35-cd40-4647-8017-1d43a270b0d1)
![파일 스트리밍 다운로드 010](https://github.com/huhdy32/edmservice/assets/101308287/ce89510b-c8ea-4baf-a1cc-42d2809a6622)
![파일 스트리밍 다운로드 011](https://github.com/huhdy32/edmservice/assets/101308287/398f97c3-4b38-4a08-833c-8f6921849fb2)
![파일 스트리밍 다운로드 012](https://github.com/huhdy32/edmservice/assets/101308287/38aa156f-c2b1-4f83-b55c-dd4ed6a40320)




## 구현 목표
1. 영상 스트리밍 서비스
- 라즈베리 파이를 이용해 실시간 영상 스트리밍 서버를 구현한다.
- LAN 에 연결된 임의의 컴퓨터에 edmServer를 구성한다.
- LAN 에 연결된 나머지 컴퓨터에 edmClient를 구성하여 edmServer에 접속한다.
- edmClient가 원하는 실시간 영상이 edmServer에서 이미 스트리밍 중이라면, 이를 공유 받는다.
- 없다면, edmServer에 요청해 새로 스트리밍을 시작한다. ( 이후, 다른 edmClient가 이를 공유받을 수 있도록 한다. )

2. 파일 다운로드 서비스
- LAN 에 연결된 임의의 컴퓨터에 edmServer를 구성한다.
- LAN 에 연결된 나머지 컴퓨터에 edmClient를 구성하여 edmServer에 접속한다.
- edmServer의 주도로 파일 다운로드를 시작한다.

## 구성
1. 서버 모듈
2. 클라이언트 모듈
3. 라즈베리 파이 스트리밍 서버

## 기술
- Java 17
- Python 3.10
- RaspberryPi 4
