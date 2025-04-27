import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class SmartCityManagement extends JFrame {

    private JComboBox<String> cityComboBox;
    private JCheckBox trafficCheckBox, weatherCheckBox, pollutionCheckBox, utilitiesCheckBox;
    private JTextArea infoTextArea;
    private JButton fetchButton;
    private JPanel infoPanel;

    // Dummy data map for city -> (category -> data)
    private Map<String, Map<String, String>> dummyData;

    public SmartCityManagement() {
        setTitle("Smart City Management");
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        initializeDummyData();

        String[] cities = {"Select City", "Mumbai", "Jaipur", "Delhi", "Kolkata", "Kerala"};
        cityComboBox = new JComboBox<>(cities);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(new JLabel("Select a City:"), gbc);

        gbc.gridy = 1;
        add(cityComboBox, gbc);

        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        trafficCheckBox = new JCheckBox("Traffic");
        weatherCheckBox = new JCheckBox("Weather");
        pollutionCheckBox = new JCheckBox("Pollution");
        utilitiesCheckBox = new JCheckBox("Utilities");

        // Visual tweak for clarity
        trafficCheckBox.setForeground(Color.RED);
        weatherCheckBox.setForeground(Color.BLUE);
        pollutionCheckBox.setForeground(Color.MAGENTA);
        utilitiesCheckBox.setForeground(new Color(0, 128, 0)); // Dark Green

        infoPanel.add(trafficCheckBox);
        infoPanel.add(weatherCheckBox);
        infoPanel.add(pollutionCheckBox);
        infoPanel.add(utilitiesCheckBox);
        infoPanel.setVisible(false);

        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(infoPanel, gbc);

        fetchButton = new JButton("Fetch Information");
        fetchButton.setEnabled(false);

        gbc.gridy = 3;
        add(fetchButton, gbc);

        infoTextArea = new JTextArea(10, 30);
        infoTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(infoTextArea);

        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(scrollPane, gbc);

        // Event Listeners
        cityComboBox.addActionListener(e -> updateUIBasedOnCitySelection());

        fetchButton.addActionListener(e -> fetchCityInformation());

        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // Setup dummy data
    private void initializeDummyData() {
        dummyData = new HashMap<>();

        String[] cities = {"Mumbai", "Jaipur", "Delhi", "Kolkata", "Kerala"};

        for (String city : cities) {
            Map<String, String> data = new HashMap<>();
            data.put("Traffic", "Traffic in " + city + ": Moderate with some congestion during peak hours.");
            data.put("Weather", "Weather in " + city + ": Sunny, 30°C.");
            data.put("Pollution", "Pollution in " + city + ": AQI 120 - Moderate.");
            data.put("Utilities", "Utilities in " + city + ": Electricity and water supply are stable.");
            dummyData.put(city, data);
        }
    }

    private void updateUIBasedOnCitySelection() {
        String selectedCity = (String) cityComboBox.getSelectedItem();
        if ("Select City".equals(selectedCity)) {
            infoTextArea.setText("Please select a city.");
            infoPanel.setVisible(false);
            fetchButton.setEnabled(false);
        } else {
            infoTextArea.setText("");
            infoPanel.setVisible(true);
            fetchButton.setEnabled(true);
        }
    }

    private void fetchCityInformation() {
        String selectedCity = (String) cityComboBox.getSelectedItem();
        if ("Select City".equals(selectedCity)) {
            infoTextArea.setText("Please select a city.");
            return;
        }

        boolean anyChecked = trafficCheckBox.isSelected() || weatherCheckBox.isSelected()
                || pollutionCheckBox.isSelected() || utilitiesCheckBox.isSelected();

        if (!anyChecked) {
            infoTextArea.setText("Please select at least one category.");
            return;
        }

        Map<String, String> cityData = dummyData.get(selectedCity);
        StringBuilder cityInfo = new StringBuilder();
        cityInfo.append("Information for ").append(selectedCity).append(":\n\n");

        if (trafficCheckBox.isSelected()) {
            cityInfo.append("- ").append(cityData.get("Traffic")).append("\n");
        }
        if (weatherCheckBox.isSelected()) {
            cityInfo.append("- ").append(cityData.get("Weather")).append("\n");
        }
        if (pollutionCheckBox.isSelected()) {
            cityInfo.append("- ").append(cityData.get("Pollution")).append("\n");
        }
        if (utilitiesCheckBox.isSelected()) {
            cityInfo.append("- ").append(cityData.get("Utilities")).append("\n");
        }

        // Add timestamp
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        cityInfo.append("\nLast Updated: ").append(LocalDateTime.now().format(formatter));

        infoTextArea.setText(cityInfo.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SmartCityManagement().setVisible(true));
    }
}
