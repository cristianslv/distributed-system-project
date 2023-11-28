# Projeto de sistemas distribuídos

Esse projeto foi desenvolvido como trabalho final da matéria de sistemas distribuídos.

O serviço que foi desenvolvido é o de echo de mensagem remota com replicação das mensagens entre servidores
e tolerância a falhas.


## Instalação

Essas são as ferramentas ideais para executar o projeto na sua máquina local:

```
  java openjdk 11
  javac 11
```

Agora falando sobre a compilação das classes do projeto, é necessário tomar
alguns cuidados. 

Primeiro, tenha certeza de que você está na pasta raíz do projeto.

Depois, execute os seguintes comandos, de forma sequencial:
```
  cd src
  rm ./*/*.class
  javac -cp '../src:../lib/*' server/Main.java
  javac -cp '../src:../lib/*' client/Main.java
```

## Uso

Em primeiro lugar, vamos falar sobre o broker MQTT.
Certifique-se de que o broker mqtt está sendo executado.

Se o broker não estiver sendo executado, em uma nova aba,
é possível executá-lo com o seguinte comando:
```
mosquitto
```

Depois de executar o broker MQTT, vamos iniciar o serviço de nomes RMI.


Agora, é muito importante validar se você está na pasta raíz do projeto.
Então, em uma nova aba, execute esses comandos de forma sequencial:
```
cd src
rmiregistry 3000
```

Finalmente, podemos ir pro sistema em si.

Novamente, valide se você está na pasta raíz do projeto. 
Então, em uma nova aba, execute esse comando para iniciar um cliente:
```
cd src
java -cp '../src:../lib/*' client.Main
```
Novamente, valide se você está na pasta raíz do projeto.
E execute, em uma nova aba, esse comando para iniciar um servidor:
```
cd src
java -cp '../src:../lib/*' server.Main
```

## Referências

- [MQTT Broker with Docker](https://dev.to/abbazs/a-step-by-step-guide-for-starting-a-mosquitto-broker-service-in-a-containers-with-docker-compose-1j8i)
- [MQTT Client in Java](https://www.baeldung.com/java-mqtt-client)
- [RMI Example in Java](https://docs.oracle.com/javase/8/docs/technotes/guides/rmi/hello/hello-world.html#create)

## Licença

[MIT](https://choosealicense.com/licenses/mit/)
