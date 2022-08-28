package org.shift.testtask;

import org.apache.commons.cli.*;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// Не успел выполнить задание полностью
// С большими файлами могут быть проблемы, если они не поместятся в оперативной памяти
// Нет проверки на пробелы
// Перепутал местами аргументы
// Старался сделать код чистым на сколько возможно, но мог лучше-мог бы раскидать методы по пакетам
// Лишние конвертации типов данных, которых желательно было избежать
// Не учитывал тот факт, что файлы были отсортированы
// Мечтал покрыть все Unit тестами, но не успел :(
// Но зато протестировал руками все, что мог
// Если не передавать аргументы, то программа будет ругатся ;)

public class Main {
    public static void main(String[] args) {
        CommandLine cmdLine = getCommandLine(args);
        String[] files = cmdLine.getArgs(); // Все аргументы, которые не перечислены
        String outFile = files[0];
        ArrayList<String> inputData = getStrings(files);
        boolean isAscending = !cmdLine.hasOption("d"); // Небольшой хак :) (так не придется проверять параметр -а)
        boolean isInteger = cmdLine.hasOption("i");
        sortAndWrite(outFile, inputData, isAscending, isInteger);
    }

    private static void sortAndWrite(String outFile, ArrayList<String> inputData, boolean isAscending, boolean isInteger) {
        try(FileWriter writer = new FileWriter(outFile)) {
           if(isInteger) {
               var result = sortIntArray(convertToInt(inputData), isAscending);
                for (int i : result) writer.write(i + "\n");
            } else {
               var result = sortStringArray(inputData, isAscending);
                for (String s : result) writer.write(s + "\n");
            }
        }
        catch (IOException e) {
            System.out.println("Ошибка записи выходных данных.Нажмите Enter для завершения программы.");
        }
    }

    private static int[] convertToInt(ArrayList<String> inputData) {
        int[] numbers = new int[inputData.size()];

        for (int i = 0; i < inputData.size(); i++) {
            try {
                numbers[i] = Integer.parseInt(inputData.get(i));
            } catch (NumberFormatException formatException) {
                System.out.println("Это не число.");
            }
        }
        return numbers;
    }

    private static ArrayList<String> getStrings(String[] files) {
        ArrayList<String> inputData = new ArrayList<>();
        for(int i = 1; i< files.length; i++) {
            inputData.addAll(readFile(files[i]));
        }
        return inputData;
    }

    private static List<String> readFile(String fileName) {
        List<String> lines = null;
        try {
             lines = Files.readAllLines(Paths.get(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("Входной файл с заданым именем не найден.Нажмите Enter для завершения программы.");
        } catch (IOException e) {
            System.out.println("Ошибка чтения входных данных.Нажмите Enter для завершения программы.");
        }
        return lines;
    }

    private static CommandLine getCommandLine(String[] args) {
        // Распарсить параметры-аргументы (Валидация параметров)
        Options options = new Options();
        DefaultParser clParse = new DefaultParser();
        OptionGroup typeGroup = new OptionGroup();
        OptionGroup sortGroup = new OptionGroup();

        sortGroup.addOption(new Option("a","Сортировка по возрастанию."));
        sortGroup.addOption(new Option("d","Сортировка по убыванию."));
        options.addOptionGroup(sortGroup);
        sortGroup.setRequired(false);

        typeGroup.addOption(new Option("i","Числовой тип данных."));
        typeGroup.addOption(new Option("s","Строковый тип данных."));
        options.addOptionGroup(typeGroup);
        typeGroup.setRequired(true);


        CommandLine cmdLine;
        try {
            cmdLine = clParse.parse(options, args);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return cmdLine;
    }

    public static int[] sortIntArray(int[] arr, boolean increase) {
        int tmp;
        for (int i = 1,j; i < arr.length; i++) {
            tmp = arr[i];
            j = i - 1;
            while (j >= 0 && (increase ? arr[j] > tmp : arr[j] < tmp)) {
                arr[j+1] = arr[j];
                j--;
            }
            arr[j+1] = tmp;
        }
        return arr;
    }

    // Сортировка массива строк
    public static String[] sortStringArray(ArrayList<String> list, boolean increase) {
        String[] arr = list.toArray(new String[0]);
        String tmp;
        for (int i = 1, j; i < arr.length; i++) {
            tmp = arr[i];
            j = i - 1;
            while (j >= 0 && (increase ? arr[j].compareTo(tmp) > 0 : arr[j].compareTo(tmp) < 0)) {
                arr[j+1] = arr[j];
                j--;
            }
            arr[j+1] = tmp;
        }
        return arr;
    }

}
