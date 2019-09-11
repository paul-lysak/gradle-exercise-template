# gradle-exercise-template

A template for building a coding excercises based on Gradle

For detailed list of commands see [common README.md](common/src/test/README.md)

To adapt it for your needs copy the project to new location, replace excercises in `.curriculum` folder with our own content,
change packages in `common/src/main/kotlin`, `common/src/test/kotlin`, place code which must 
be accessible for all exercises in the `common` sub-project - main and test in corresponding folders.
Optionally create corresponding package in `exercises/src/main/kotlin`.

When switching to the new exercise the folder `exercises/src/test` gets replaced with corresponding folder from the
exercise. Main code isn't affected. 
