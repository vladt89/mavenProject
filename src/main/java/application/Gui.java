package application;

import repository.PersonEntity;
import service.RunningServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.Map;

/**
 * User interface for the application.
 *
 * @author vladimir.tikhomirov
 */
public class Gui {

    private static final int AMOUNT_OF_COLUMNS = 4;
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
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showOpenDialog(button) == JFileChooser.APPROVE_OPTION) {
                    idToPersonEntityMap = runningService.getParser().parseCsvFile(fileChooser.getSelectedFile().getPath());
                }
                analyzeAndShowPersonsData();
            }
        });
        return button;
    }

    private void analyzeAndShowPersonsData() {
        for (Integer personId : idToPersonEntityMap.keySet()) {
            PersonEntity personEntity = idToPersonEntityMap.get(personId);
            personEntity.setSalary(runningService.getWageService().calculateTotalSalary(personEntity));
        }
        if (idToPersonEntityMap != null) {
            JTable table = createTable();
            table.setModel(fillTable());
        }
    }

    private JTable createTable() {
        JTable table = new JTable(idToPersonEntityMap.size(), AMOUNT_OF_COLUMNS);
        table.setPreferredScrollableViewportSize(new Dimension(400, 70));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
        frame.revalidate();
        return table;
    }

    private TableModel fillTable() {
        java.util.List<String> columns = new LinkedList<>();
        columns.add("ID");
        columns.add("Employee");
        columns.add("Total Salary");

        java.util.List<String[]> values = new LinkedList<>();
        for (int i = 1; i <= idToPersonEntityMap.size(); i++) {
            PersonEntity personEntity = idToPersonEntityMap.get(i);
            values.add(new String[] {
                    String.valueOf(personEntity.getId()),
                    personEntity.getName(),
                    String.valueOf(personEntity.getSalary())
                    });
        }
        return new DefaultTableModel(values.toArray(new Object[][] {}), columns.toArray());
    }
}




