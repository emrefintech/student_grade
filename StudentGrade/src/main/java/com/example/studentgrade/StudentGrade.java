package com.example.studentgrade;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

public class StudentGrade extends Application {
    private GradeBase gradeBase;
    private TableView<Student> tableView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        this.tableView = new TableView<>();
        this.gradeBase = new GradeBase();




        primaryStage.setTitle("Student Information");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        Label nameLabel = new Label("Student Name:");
        TextField nameField = new TextField();
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);

        Label numberLabel = new Label("Student Number:");
        TextField numberField = new TextField();
        grid.add(numberLabel, 0, 1);
        grid.add(numberField, 1, 1);

        Label gradeLabel = new Label("Student Grade:");
        TextField gradeField = new TextField();
        grid.add(gradeLabel, 0, 2);
        grid.add(gradeField, 1, 2);

        Label attendingLabel = new Label("Is Student Attending:");
        CheckBox attendingCheckBox = new CheckBox();
        grid.add(attendingLabel, 0, 3);
        grid.add(attendingCheckBox, 1, 3);

        Label genderLabel = new Label("Student Gender:");
        ComboBox<String> genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female");
        grid.add(genderLabel, 0, 4);
        grid.add(genderComboBox, 1, 4);


        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {

            if (!isString(nameField.getText())) {
                System.err.println("Error: Only text is allowed in the name field.");
                return;
            }
            if (!isInteger(numberField.getText())) {
                System.err.println("Error: Only numbers are allowed.");
                return;
            }

            if (!isFloat(gradeField.getText())) {
                System.err.println("Error: Only enter grades.");
                return;
            }
            if (!isComboBoxSelected(genderComboBox)) {
                System.err.println("Error: Please provide gender information.");
                return;
            }


            String name = nameField.getText();
            int number = Integer.parseInt(numberField.getText());
            float grade = Float.parseFloat(gradeField.getText());
            boolean attending = attendingCheckBox.isSelected();
            char gender = genderComboBox.getValue().charAt(0);


            Student student;
            // Create appropriate class based on gender
            if (gender == 'M') {
                student = new MaleStudent(name, number, grade, attending, gender);
            } else if (gender == 'F') {
                student = new FemaleStudent(name, number, grade, attending, gender);
            } else {
                // Error status
                System.err.println("Incorrect gender information.");
                return;
            }

            // Add student to GradeBase
            gradeBase.addStudent(student);

            // Clear information
            nameField.clear();
            numberField.clear();
            gradeField.clear();
            attendingCheckBox.setSelected(false);
            genderComboBox.getSelectionModel().clearSelection();
        });

        grid.add(submitButton, 0, 5);

        Button viewButton = new Button("View Student Information");
        viewButton.setOnAction(e -> {
            showStudentInfo();
        });
        grid.add(viewButton, 1, 5);

        Scene scene = new Scene(grid, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private boolean isString(String text) {
        // If the incoming text is not null and contains only letters, it is a text.
        return text != null && text.matches("[a-zA-Z]+");
    }
    private boolean isInteger(String text) {
        try {
            // Try to convert the entered text to an integer
            Integer.parseInt(text);

            return true;
        } catch (NumberFormatException e) {

            return false;
        }
    }
    private boolean isFloat(String text) {
        try {
            // Try to convert the entered text to a float value
            float value = Float.parseFloat(text);
            return value >= 0.0 && value <= 4.0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private boolean isComboBoxSelected(ComboBox<?> comboBox) {
        Boolean bool = false;
        try {

            // Check if there is an item selected in the ComboBox
            if (comboBox.getValue() != null)
                bool = true;
            return bool;
        } catch (Exception e) {
            return false;
        }

    }



    public void showStudentInfo() {
        // Create a window to show student information
        Stage stage = new Stage();
        stage.setWidth(800); // Width
        stage.setHeight(600); // Height

        stage.setTitle("Student information");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        List<Student> students = gradeBase.getStudents();


        TableView<Student> tableView = new TableView<>();
        tableView.setItems(FXCollections.observableArrayList(students));

        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Student, Integer> numberColumn = new TableColumn<>("Number");
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));

        TableColumn<Student, Float> gradeColumn = new TableColumn<>("Grade");
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));

        TableColumn<Student, Boolean> attendingColumn = new TableColumn<>("Does Attend School?");
        attendingColumn.setCellValueFactory(new PropertyValueFactory<>("attending"));

        TableColumn<Student, Character> genderColumn = new TableColumn<>("Gender");
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        TableColumn<Student, Void> updateColumn = new TableColumn<>("Update");
        updateColumn.setCellFactory(param -> new TableCell<Student, Void>() {
            private final Button updateButton = new Button("Update");

            {
                updateButton.setOnAction(event -> {
                    Student student = getTableView().getItems().get(getIndex());
                    openUpdateDialog(student,tableView);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(updateButton);
                }
            }
        });

        Button showInConsoleButton = new Button("Show in Console");
        showInConsoleButton.setOnAction(event -> gradeBase.showStudentsInConsole());
        grid.add(showInConsoleButton, 1, 5);

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> {
            Student selectedStudent = tableView.getSelectionModel().getSelectedItem();
            if (selectedStudent != null) {
                gradeBase.removeStudent(selectedStudent);
                tableView.getItems().remove(selectedStudent);
            }
        });
        grid.add(deleteButton, 2, 5);


        tableView.getColumns().addAll(nameColumn, numberColumn, gradeColumn, attendingColumn, genderColumn, updateColumn);
        grid.add(tableView, 0, 0);

        Scene scene = new Scene(grid, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void openUpdateDialog(Student oldStudent, TableView<Student> tableView) {
        Stage updateStage = new Stage();
        updateStage.setTitle("Update Student Information");

        // Create update screen
        GridPane updateGrid = new GridPane();
        updateGrid.setHgap(10);
        updateGrid.setVgap(10);

        // Create components on the update screen
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField(oldStudent.getName());
        Label numberLabel = new Label("Number:");
        TextField numberField = new TextField(String.valueOf(oldStudent.getNumber()));
        Label gradeLabel = new Label("Grade:");
        TextField gradeField = new TextField(String.valueOf(oldStudent.getGrade()));
        Label attendingLabel = new Label("Attending to School:");
        CheckBox attendingCheckBox = new CheckBox();
        attendingCheckBox.setSelected(oldStudent.isAttending());
        Label genderLabel = new Label("Gender:");
        ComboBox<String> genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female");
        genderComboBox.setValue(String.valueOf(oldStudent.getGender()));


        Button updateButton = new Button("Update");

        updateButton.setOnAction(e -> {

            if (!isString(nameField.getText())) {
                System.err.println("Error: Only text is allowed in the name field.");
                return;
            }
            if (!isInteger(numberField.getText())) {
                System.err.println("Error: Only numbers are allowed.");
                return;
            }

            if (!isFloat(gradeField.getText())) {
                System.err.println("Error: Only enter grades.");
                return;
            }
            if (!isComboBoxSelected(genderComboBox)) {
                System.err.println("Error: Please provide gender information.");
                return;
            }


            if (genderComboBox.getValue().charAt(0)=='F') {
                // Create a new Student object

                Student newStudent = new FemaleStudent(
                        nameField.getText(),
                        Integer.parseInt(numberField.getText()),
                        Float.parseFloat(gradeField.getText()),
                        attendingCheckBox.isSelected(),
                        genderComboBox.getValue().charAt(0)
                );


                // Perform the update
                updateStudentGrade(oldStudent, newStudent, tableView);


                updateStage.close();
            }
            else
            {

                Student newStudent = new MaleStudent(
                        nameField.getText(),
                        Integer.parseInt(numberField.getText()),
                        Float.parseFloat(gradeField.getText()),
                        attendingCheckBox.isSelected(),
                        genderComboBox.getValue().charAt(0)
                );


                updateStudentGrade(oldStudent, newStudent, tableView);

                updateStage.close();
            }

        });

        updateGrid.addRow(0, nameLabel, nameField);
        updateGrid.addRow(1, numberLabel, numberField);
        updateGrid.addRow(2, gradeLabel, gradeField);
        updateGrid.addRow(3, attendingLabel, attendingCheckBox);
        updateGrid.addRow(4, genderLabel, genderComboBox);
        updateGrid.addRow(5, updateButton);

        Scene updateScene = new Scene(updateGrid, 400, 250);
        updateStage.setScene(updateScene);
        updateStage.show();
    }





    private void updateStudentGrade(Student oldStudent, Student newStudent, TableView<Student> updatedTableView) {
        UpdateBase updateBase = new UpdateBase(gradeBase, this);
        updateBase.updateStudentGrade(oldStudent, newStudent);
        updateStudentInfo(updatedTableView);
    }



    private void updateStudentInfo(TableView<Student> updatedTableView) {
        List<Student> students = gradeBase.getStudents();
        updatedTableView.setItems(FXCollections.observableArrayList(students));
    }




}
