--------------------------------------------------------------------------------------------------------------------------

API - Método de Instalação
1 - Colocar a pasta da API no seu provedor local de servidor APACHE (XAMPP, WAMP, LARAGON)

2 - Executar os seguintes comandos (Dentro do diretório da pasta da API):
	php init (Responder às questões com: 0, Yes, No, No, No, No, No)
	Composer install
	Composer update

3 - Aceder ao seguinte link:
	https://mosquitto.org/files/binary/win64/mosquitto-2.0.14-install-windows-x64.exe
	Instalar o respetivo ficheiro executável (MQTT)

4 - Executar o ficheiro "CarBuddyWithDataMySQL.sql" no seu provedor MySQL (Base de dados e respetivos registos)

5 - Lançar o servidor da API executando o seguinte comando: 
	php yii serve 127.0.0.1:8000 --docroot="frontend/web" (Dentro do diretório da pasta da API)

6 - Lançar o servidor MQTT executando o seguinte comando:
	mosquitto.exe (Dentro do seguinte diretório: C:\ProgramFiles\mosquitto)

--------------------------------------------------------------------------------------------------------------------------

APP - Método de Instalação
1 - Abrir o Projeto no Android Studio e executar (O gradle vai automáticamente instalar todas as bibliotecas necessárias)

--------------------------------------------------------------------------------------------------------------------------

Credenciais de Acesso (APP)

Utilizadores Previamente Registados na Base de Dados
Com dados (carros, reparações e agendamentos)

Lista de Utilizadores:

Cliente
User: Goncalo
Password: 12345678

Cliente
User: Goncalo22
Password: 12345678

Cliente
User: Goncalo123
Password: 12345678

Cliente
User: Goncalo1234
Password: 12345678

Cliente
User: Goncalo12345678
Password: 12345678

--------------------------------------------------------------------------------------------------------------------------
