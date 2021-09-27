# # Doctor for Everyone

<!---Esses são exemplos. Veja https://shields.io para outras pessoas ou para personalizar este conjunto de escudos. Você pode querer incluir dependências, status do projeto e informações de licença aqui--->

![GitHub repo size](https://img.shields.io/github/repo-size/jaisonklemer/projeto-final-serasa)

<p>
  <img src="https://github.com/jaisonklemer/projeto-final-serasa/blob/master/images/Mockup%20doctor%20png.png" />
</p>

## Sumário
- [Sobre o Projeto](#sobre-o-projeto)
- [Pré-requisitos](#-pré-requisitos)
- [Sobre o App](#sobre-o-app)
- [Passo a Passo](#passo-a-passo)
- [Mockup](#mockup)
- [Dependências usadas](#dependências-usadas)
- [Baixe o Apk](#baixe-o-apk)
- [Implementações futuras](#implementações-futuras)

### Sobre o Projeto
- Projeto final para o curso de Codigo para Todxs Versao Mobile - Serasa

## 💻 Pré-requisitos

Antes de começar, verifique se você atendeu aos seguintes requisitos:
<!---Estes são apenas requisitos de exemplo. Adicionar, duplicar ou remover conforme necessário--->
* Baixar Android Studio: [AQUI](https://developer.android.com/studio)
* Clonar o repositório: ```git clone https://github.com/jaisonklemer/projeto-final-serasa.git```
* Abrir Android Studio.
* Abrir o repositório onde está o projeto.
* Sincronizar o Gradle.
* Executar o App.

## Sobre o App
- O objetivo principal do DoctorsForEveryone é a agilidade para agendar uma consulta com algum especialista na área da saúde.
- Com praticidade e rapidez, é possível selecionar o especialista desejado, escolher uma data e horário disponível e realizar o agendamento. 
- É possível também acompanhar cada agendamento e até mesmo cancelar.

## Passo-a-Passo
1. Criar conta ou logar caso já exista.
2. Completar os dados de cadastro no perfil.
3. Pode selecionar um médico de prefenrência e filtrar por especialidade.
4. Depois de escolhido o médico é possivel escolher data e hora para a consulta.
5. É possível verificar quais consultas já foram agendadas/canceladas/concluídas.
6. Temos também uma aba de notícias sobre a saúde.

## Mockup
<p>
  <img src="https://github.com/jaisonklemer/projeto-final-serasa/blob/master/images/Page1.png" />
  <img src="https://github.com/jaisonklemer/projeto-final-serasa/blob/master/images/Page2.png" />
  <img src="https://github.com/jaisonklemer/projeto-final-serasa/blob/master/images/Page3.png" />
</p>

## Dependências usadas
```sh
    //Retrofit dependencies
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

    //ViewModel & Lifecycle
    implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.3.1'
    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1'

    //Firebase dependencies
    implementation 'com.google.firebase:firebase-auth-ktx:21.0.1'
    implementation 'com.google.firebase:firebase-firestore-ktx:23.0.3'

    //Glide dependencies
    implementation 'com.github.bumptech.glide:glide:4.12.0'
    kapt 'com.github.bumptech.glide:compiler:4.12.0'

    //Tests
    testImplementation 'junit:junit:4.+'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'

    // Import the BoM for the Firebase platform
    implementation platform('com.google.firebase:firebase-bom:28.4.0')
    implementation "io.grpc:grpc-okhttp:1.32.2"

    // Import dependency for the Google Play services
    implementation 'com.google.android.gms:play-services-auth:19.2.0'

    //Flexbox
    implementation 'com.google.android.flexbox:flexbox:3.0.0'

    //Datepicker
    implementation 'com.github.gastricspark:scrolldatepicker:0.0.1'

    //Recycler view for using concat adapter
    implementation "androidx.recyclerview:recyclerview:1.2.1"
    
    //Lottie animation
    def lottieVersion = "3.4.0"
    implementation "com.airbnb.android:lottie:$lottieVersion"

    //Hilt
    implementation "com.google.dagger:hilt-android:2.37"
    kapt "com.google.dagger:hilt-android-compiler:2.37"

    //Network Watch
    implementation 'com.github.draxdave:netWatch:0.d.21'
```
## Baixe o Apk
- Apk: [AQUI](https://drive.google.com/file/d/14bR_dVVt-05-QceZyA1Ppe27cMPmvBw5/view?usp=sharing)

### Implementações futuras
- Push para Notificações.
- Favoritar doutores.
- Tela de favoritos.
- Tela para cadastrar especialidades e doutores.
- Fazer um app de gerenciamento voltado para os doutores.
