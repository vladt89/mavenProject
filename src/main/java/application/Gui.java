package application;

import repository.PersonEntity;
import service.RunningServiceImpl;
import service.parser.CsvParsingException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * User interface for the application.
 *
 * @author vladimir.tikhomirov
 */
public class Gui {

    private final RunningServiceImpl runningService;
    private final JPanel panel;
    private final JFrame frame;
    private Map<Integer, PersonEntity> idToPersonEntityMap;

    public Gui(RunningServiceImpl runningService) {
        this.runningService = runningService;

        frame = new JFrame("Wage system");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500, 500));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        panel = new JPanel();
        panel.add(createLoadButton());
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JButton createLoadButton() {
        final JButton button = new JButton("Load csv file");
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                final JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showOpenDialog(button) == JFileChooser.APPROVE_OPTION) {
                    String pathToFile = fileChooser.getSelectedFile().getPath();
                    if (FileValidator.validate(pathToFile)) {
                        try {
                            idToPersonEntityMap = runningService.getParser().parseCsvFile(pathToFile);
                        } catch (CsvParsingException e) {
                            showErrorMessage(e.getMessage());
                            return;
                        }
                        analyzeAndShowPersonsData();
                    } else {
                        showErrorMessage("File type is wrong. Please, load csv file, other types are no supported.");
                    }
                }
            }
        });
        return button;
    }

    private static void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message, "Error Dialog", JOptionPane.ERROR_MESSAGE);
    }

    private void analyzeAndShowPersonsData() {
        for (Integer personId : idToPersonEntityMap.keySet()) {
            PersonEntity personEntity = idToPersonEntityMap.get(personId);
            personEntity.setSalaryPerMonth(runningService.getWageService().calculateSalariesPerMonth(personEntity));
        }
        if (!idToPersonEntityMap.isEmpty()) {
            JLabel monthLabel = new JLabel("Month: ");
            panel.add(monthLabel);
            JComboBox dropDownList = new JComboBox();
            Set<Integer> monthSet = runningService.getWageService().getMonthSet();
            for (Integer month : monthSet) {
                dropDownList.addItem(month + 1);
            }
            panel.add(dropDownList);
            frame.revalidate();

            dropDownList.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JComboBox comboBox = (JComboBox) e.getSource();
                    Integer selectedMonth = (Integer) comboBox.getSelectedItem() - 1;

                    JTable table = createTable();
                    table.setModel(fillTable(selectedMonth));
                }
            });
        } else {
            showErrorMessage("Unfortunately the parsing data is empty. Please, verify if you load a proper file and try again.");
        }
    }

    private JTable createTable() {
        JTable table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(400, 70));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
        frame.revalidate();
        return table;
    }

    private TableModel fillTable(Integer month) {
        java.util.List<String> columns = new LinkedList<>();
        columns.add("ID");
        columns.add("Employee");
        columns.add("Total Salary");

        java.util.List<String[]> values = new LinkedList<>();
        for (int i = 1; i <= idToPersonEntityMap.size(); i++) {
            PersonEntity personEntity = idToPersonEntityMap.get(i);
            Double salaryPerMonth = personEntity.getSalaryPerMonth()[month];
            if (salaryPerMonth != 0.0) {
                values.add(new String[]{
                        String.valueOf(personEntity.getId()),
                        personEntity.getName(),
                        String.valueOf(salaryPerMonth)
                });
            }
        }
        return new DefaultTableModel(values.toArray(new Object[][] {}), columns.toArray());
    }
}




