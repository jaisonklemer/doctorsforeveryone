# # Doctor for Everyone

<!---Esses são exemplos. Veja https://shields.io para outras pessoas ou para personalizar este conjunto de escudos. Você pode querer incluir dependências, status do projeto e informações de licença aqui--->

![GitHub repo size](https://img.shields.io/github/repo-size/jaisonklemer/projeto-final-serasa)

<p>
  <img src="https://github.com/jaisonklemer/projeto-final-serasa/blob/master/images/Mockup%20doctor%20png.png" />
</p>

## Sumario
- [Sobre o Projeto](Sobre-o-Projeto)
- [Pré-requisitos](💻-Pré-requisitos)
- [Sobre o App](Sobre-o-App)
- [Screenshots](Screenshots)
- [Dependencias usadas](Dependencias-usadas)
- [Baixe o Apk](Baixe-o-Apk)


### Sobre o Projeto
- Projeto final para o curso de Codigo para Todxs Versao Mobile - Serasa

## 💻 Pré-requisitos

Antes de começar, verifique se você atendeu aos seguintes requisitos:
<!---Estes são apenas requisitos de exemplo. Adicionar, duplicar ou remover conforme necessário--->
* Baixar AndroidStudio: [AQUI](https://developer.android.com/studio)
* Clonar o repositoruo: ```git clone https://github.com/jaisonklemer/projeto-final-serasa.git```
* Sincronizar o Gradle
* Executar o App

## Sobre o App
- Tem o foco em facilitar a vida tanto dos doutores quanto dos pacientes.
- Através do mesmo é possivel agendar consultas com qualquer doutor disponivel na plataforma.

## Screenshots


## Dependencias usadas
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
```
## Baixe o Apk
- Apk: [AQUI](link.com)

### Implementações futuras 
- 
