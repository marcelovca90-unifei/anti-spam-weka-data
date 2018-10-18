# anti-spam-weka-data

Data sets used by the project of my master's degree in Computer Science ("Study and Research in Anti-Spam Systems").

### Prerequisites:

[Git](https://git-scm.com/downloads) + [P7ZIP](https://packages.ubuntu.com/p7zip-full) + [JDK 8](http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html) / [OpenJDK 8](http://openjdk.java.net/install/) + [Apache Maven](https://maven.apache.org/download.cgi)

#### 1. Cloning and building the main project ([anti-spam-weka-cli](https://github.com/marcelovca90/anti-spam-weka-cli)):

```
git clone https://github.com/marcelovca90/anti-spam-weka-cli ~/anti-spam-weka-cli/
cd ~/anti-spam-weka-cli/AntiSpamWekaCLI/ && mvn clean install
```

#### 2. Cloning and extracting the data sets ([anti-spam-weka-data](https://github.com/marcelovca90/anti-spam-weka-data)):

```
git clone https://github.com/marcelovca90/anti-spam-weka-data ~/anti-spam-weka-data/
cd ~/anti-spam-weka-data/ && 7z x LING_SPAM.7z
cd ~/anti-spam-weka-data/ && 7z x SPAM_ASSASSIN.7z
cd ~/anti-spam-weka-data/ && cat TREC.7z.part-* > TREC.7z && 7z x TREC.7z -pPASSWORD
cd ~/anti-spam-weka-data/ && cat UNIFEI_CRUDE.7z.part-* > UNIFEI_CRUDE.7z && 7z x UNIFEI_CRUDE.7z -pPASSWORD
cd ~/anti-spam-weka-data/ && cat UNIFEI_DELTA_0.7z.part-* > UNIFEI_DELTA_0.7z && 7z x UNIFEI_DELTA_0.7z -pPASSWORD
cd ~/anti-spam-weka-data/ && cat UNIFEI_DELTA_3.7z.part-* > UNIFEI_DELTA_3.7z && 7z x UNIFEI_DELTA_3.7z -pPASSWORD
cd ~/anti-spam-weka-data/ && cat UNIFEI_DELTA_7.7z.part-* > UNIFEI_DELTA_7.7z && 7z x UNIFEI_DELTA_7.7z -pPASSWORD
```

#### 3. [LINUX] Installing the high-performance linear algebra library ([netlib-java](https://github.com/fommil/netlib-java)):

```
sudo apt-get install libatlas3-base libopenblas-base
sudo update-alternatives --config libblas.so
sudo update-alternatives --config libblas.so.3
sudo update-alternatives --config liblapack.so
sudo update-alternatives --config liblapack.so.3
```

#### 4. Configuring and performing training and testing

```
cd ~/anti-spam-weka-cli/AntiSpamWekaCLI/ && vi run.properties
java -Xms<INITIAL_HEAP_SIZE> -Xmx<MAXIMUM_HEAP_SIZE> -Xss<STACK_SIZE> -jar ./target/AntiSpamWekaCLI-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```
