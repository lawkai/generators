# Project Facade Generators

This project uses [plopjs][1] to initialize project based on specific templates. 

## Installation
1. Clone this repo to a local folder
    ```
    git clone git@github.com:lawkai/generators.git
   ```
2.  Install [plopjs][1] globally
    ``` 
    npm install -g plop
    ```
3. Set up an alias in your bash profile or zsh profile
    ```
    alias gen='plop --plopfile <path-to-your-generators/plopfile> --dest .'
    ```
4. Restart your terminal and then run 'gen' in any directory to create a new project based on the template.

## Templates
1. kotlin-jvm
   * Creates a kotlin jvm application with a simple Hello World.
   * Uses gradle 5.6.3 and kotlin DSL
   * Based on Kotlin 1.3+ with Coroutines support.

[1]: https://plopjs.com
