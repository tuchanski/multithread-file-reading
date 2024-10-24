# Multithread File Reading 🧵

## Character Frequency Counter using Threads

This project is a Java application that counts the frequency of each letter (a-z) across multiple text files using multithreading. The program processes each file in a separate thread to speed up the counting process, with thread synchronization to handle concurrent updates to the shared data structure.

## Features

- **Multithreading**: Each file is processed in parallel using threads, improving efficiency for large datasets.
- **Thread Synchronization**: Ensures that the shared character frequency map is updated correctly when accessed by multiple threads.
- **Performance Timing**: Measures and prints the total time taken to process all files.

## Usage

1. The directory called `todosArquivos` has 35k of txt files.
2. Run the application. It will count the frequency of each letter from all files in the directory and print the result.
3. The total execution time (in ms) is displayed at the end.

## How to Run

1. Clone the repository.
   ```
   git clone https://github.com/tuchanski/multithread-file-reading.git
   ```
2. Open the project in your favorite Java IDE.
3. Run the `Main` class.

## Technology

Programming language: **Java**

## Author

- [Guilherme Tuchanski Rocha | GitHub](https://github.com/tuchanski)

