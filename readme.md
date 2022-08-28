# Test task for my DreamJob

## Инструкция

Версия Java 18.0.2
Сборщик Maven версия 4.0.0
Используемые библиотеки:
https://commons.apache.org/proper/commons-cli/
<dependency>
            <groupId>commons-cli</groupId>
            <artifactId>commons-cli</artifactId>
            <version>1.5.0</version>
</dependency>

Параметры программы задаются при запуске через аргументы командной строки, по порядку:
1. тип данных (-s или -i), обязательный;
2. режим сортировки (-a или -d), необязательный, по умолчанию сортируем по возрастанию;
3. имя выходного файла, обязательное;
4. остальные параметры – имена входных файлов, не менее одного.

Пример запуска:
java -jar Kolotovkin-Ilya.jar -s -d out.txt 1.txt 2.txt


