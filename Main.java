import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/* =============================================================================
 * PROJECT: LAB SAFETY GUARD - OOP MICROPROJECT (SINGLE FILE VERSION)
 * FILE: Main.java (Application Entry Point & Supporting Classes)
 * =============================================================================
 */

// =============================================================================
// ENUMS & INTERFACES
// =============================================================================

enum EquipmentStatus {
    AVAILABLE,
    IN_USE,
    NEEDS_MAINTENANCE
}

enum RiskLevel {
    LOW,
    MEDIUM,
    HIGH
}

interface SafetyRule {
    boolean evaluateRule(SafetyChecklist checklist);

    String getRuleDescription();
}

// =============================================================================
// SUPPORTING MODEL CLASSES
// =============================================================================

class SafetyChecklist {
    private boolean labCoat;
    private boolean goggles;
    private boolean gloves;
    private boolean cleanArea;
    private String notes;

    public SafetyChecklist(boolean labCoat, boolean goggles, boolean gloves, boolean cleanArea, String notes) {
        this.labCoat = labCoat;
        this.goggles = goggles;
        this.gloves = gloves;
        this.cleanArea = cleanArea;
        this.notes = notes;
    }

    public boolean isLabCoat() {
        return labCoat;
    }

    public boolean isGoggles() {
        return goggles;
    }

    public boolean isGloves() {
        return gloves;
    }

    public boolean isCleanArea() {
        return cleanArea;
    }

    public String getNotes() {
        return notes;
    }
}

class ViolationRecord {
    private String violationType;
    private RiskLevel riskLevel;
    private String description;

    public ViolationRecord(String violationType, RiskLevel riskLevel, String description) {
        this.violationType = violationType;
        this.riskLevel = riskLevel;
        this.description = description;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void displayViolationDetails() {
        System.out.println("  * Type        : " + violationType);
        System.out.println("  * Risk Level  : " + riskLevel);
        System.out.println("  * Description : " + description);
    }
}

class SafetyCheckRecord {
    private String checkId;
    private LocalDateTime timestamp;
    private String memberId;
    private String memberName;
    private String memberRole;
    private boolean passed;
    private RiskLevel riskLevel;
    private List<ViolationRecord> violations;

    public SafetyCheckRecord(String checkId, String memberId, String memberName, String memberRole,
            boolean passed, RiskLevel riskLevel, List<ViolationRecord> violations) {
        this.checkId = checkId;
        this.timestamp = LocalDateTime.now();
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberRole = memberRole;
        this.passed = passed;
        this.riskLevel = riskLevel;
        this.violations = violations;
    }

    public String getCheckId() {
        return checkId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getMemberRole() {
        return memberRole;
    }

    public boolean isPassed() {
        return passed;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public List<ViolationRecord> getViolations() {
        return violations;
    }

    public void displaySummary() {
        System.out.println("\n------------------------------------------------------------");
        System.out.println("                  SAFETY AUDIT RESULT SUMMARY               ");
        System.out.println("------------------------------------------------------------");
        System.out.println("Check ID   : " + checkId);
        System.out.println("Timestamp  : " + timestamp);
        System.out.println("Member     : " + memberName + " (" + memberId + ")");
        System.out.println("Role       : " + memberRole);
        System.out.println("Status     : " + (passed ? "PASSED (Safe)" : "FAILED (Violations Found)"));
        System.out.println("Max Risk   : " + riskLevel);
        if (!violations.isEmpty()) {
            System.out.println("Violations Recorded: " + violations.size());
        }
        System.out.println("------------------------------------------------------------");
    }
}

class LabEquipment {
    private String equipmentId;
    private String name;
    private String category;
    private String location;
    private EquipmentStatus status;
    private String lastMaintenanceNote;

    public LabEquipment(String equipmentId, String name, String category, String location, EquipmentStatus status) {
        this.equipmentId = equipmentId;
        this.name = name;
        this.category = category;
        this.location = location;
        this.status = status;
        this.lastMaintenanceNote = "Initial registration - Good condition";
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getLocation() {
        return location;
    }

    public EquipmentStatus getStatus() {
        return status;
    }

    public String getLastMaintenanceNote() {
        return lastMaintenanceNote;
    }

    public boolean updateStatus(EquipmentStatus newStatus, String note) {
        if (newStatus != null) {
            this.status = newStatus;
            if (note != null && !note.isEmpty()) {
                this.lastMaintenanceNote = note;
            }
            return true;
        }
        return false;
    }

    public void displayEquipmentDetails() {
        System.out.printf("| %-8s | %-24s | %-16s | %-12s | %-18s |\n",
                equipmentId, name, category, location, status);
    }
}

// =============================================================================
// INHERITANCE HIERARCHY (LAB MEMBER & SUBCLASSES)
// =============================================================================

abstract class LabMember {
    protected String memberId;
    protected String name;
    protected String department;
    protected String contact;
    protected String role;

    public LabMember(String memberId, String name, String department, String contact, String role) {
        this.memberId = memberId;
        this.name = name;
        this.department = department;
        this.contact = contact;
        this.role = role;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getContact() {
        return contact;
    }

    public String getRole() {
        return role;
    }

    public abstract String getClearanceLevel();

    public abstract void displayDetails();

    public abstract SafetyCheckRecord performSafetyCheck(SafetyChecklist checklist, String checkId);
}

class Student extends LabMember {
    private String rollNo;
    private int semester;
    private String program;

    public Student(String memberId, String name, String department, String contact,
            String rollNo, int semester, String program) {
        super(memberId, name, department, contact, "Student");
        this.rollNo = rollNo;
        this.semester = semester;
        this.program = program;
    }

    @Override
    public String getClearanceLevel() {
        return "Level 1 (Standard Student Access)";
    }

    @Override
    public void displayDetails() {
        System.out.printf("| %-8s | %-18s | %-13s | %-10s | %-7s | %-15s | %-15s |\n",
                memberId, name, role, department, rollNo, "Sem: " + semester, program);
    }

    @Override
    public SafetyCheckRecord performSafetyCheck(SafetyChecklist checklist, String checkId) {
        List<ViolationRecord> violations = new ArrayList<>();
        RiskLevel maxRisk = RiskLevel.LOW;

        if (!checklist.isLabCoat()) {
            violations.add(new ViolationRecord("Missing Lab Coat", RiskLevel.MEDIUM,
                    "Student not wearing a fastened lab coat."));
            maxRisk = RiskLevel.MEDIUM;
        }
        if (!checklist.isGoggles()) {
            violations.add(new ViolationRecord("Eye Protection Failure", RiskLevel.HIGH,
                    "Safety goggles not worn in active chemistry zone."));
            maxRisk = RiskLevel.HIGH;
        }
        if (!checklist.isGloves()) {
            violations.add(new ViolationRecord("Missing Gloves", RiskLevel.MEDIUM,
                    "Handling chemical/biological agents barehanded."));
            if (maxRisk != RiskLevel.HIGH)
                maxRisk = RiskLevel.MEDIUM;
        }
        if (!checklist.isCleanArea()) {
            violations.add(new ViolationRecord("Workstation Clutter", RiskLevel.LOW,
                    "Workbench untidy or food/drink present."));
        }

        boolean passed = violations.isEmpty();
        return new SafetyCheckRecord(checkId, memberId, name, role, passed, maxRisk, violations);
    }
}

class LabAssistant extends LabMember {
    private String badgeId;
    private String labName;
    private String shift;

    public LabAssistant(String memberId, String name, String department, String contact,
            String badgeId, String labName, String shift) {
        super(memberId, name, department, contact, "Lab Assistant");
        this.badgeId = badgeId;
        this.labName = labName;
        this.shift = shift;
    }

    @Override
    public String getClearanceLevel() {
        return "Level 2 (Supervisory & Equipment Access)";
    }

    @Override
    public void displayDetails() {
        System.out.printf("| %-8s | %-18s | %-13s | %-10s | %-7s | %-15s | %-15s |\n",
                memberId, name, role, department, badgeId, labName, shift + " Shift");
    }

    @Override
    public SafetyCheckRecord performSafetyCheck(SafetyChecklist checklist, String checkId) {
        List<ViolationRecord> violations = new ArrayList<>();
        RiskLevel maxRisk = RiskLevel.LOW;

        if (!checklist.isLabCoat()) {
            violations.add(new ViolationRecord("Staff Lab Coat Violation", RiskLevel.HIGH,
                    "Lab Assistant failed to wear mandatory lab coat."));
            maxRisk = RiskLevel.HIGH;
        }
        if (!checklist.isGoggles()) {
            violations.add(new ViolationRecord("Staff Eye Protection Failure", RiskLevel.HIGH,
                    "Lab Assistant operating without safety goggles."));
            maxRisk = RiskLevel.HIGH;
        }
        if (!checklist.isGloves()) {
            violations.add(new ViolationRecord("Staff Glove Violation", RiskLevel.MEDIUM,
                    "Lab Assistant handling gear without required gloves."));
            if (maxRisk != RiskLevel.HIGH)
                maxRisk = RiskLevel.MEDIUM;
        }
        if (!checklist.isCleanArea()) {
            violations.add(new ViolationRecord("Supervised Lab Untidy", RiskLevel.MEDIUM,
                    "Assigned laboratory zone left disorganized."));
            if (maxRisk != RiskLevel.HIGH)
                maxRisk = RiskLevel.MEDIUM;
        }

        boolean passed = violations.isEmpty();
        return new SafetyCheckRecord(checkId, memberId, name, role, passed, maxRisk, violations);
    }
}

// =============================================================================
// MANAGER CLASS
// =============================================================================

class LabSafetyManager {
    private List<LabMember> members;
    private List<LabEquipment> equipment;
    private List<SafetyCheckRecord> checkRecords;
    private List<ViolationRecord> violations;

    private int memberIdCounter = 101;
    private int checkIdCounter = 5001;

    public LabSafetyManager() {
        this.members = new ArrayList<>();
        this.equipment = new ArrayList<>();
        this.checkRecords = new ArrayList<>();
        this.violations = new ArrayList<>();

        // Seed some initial equipment data for testing
        equipment.add(new LabEquipment("EQ-01", "Digital Spectrophotometer", "Analytical", "Lab Room 3B",
                EquipmentStatus.AVAILABLE));
        equipment.add(new LabEquipment("EQ-02", "Fume Hood Alpha", "Safety Ventilation", "Lab Room 1A",
                EquipmentStatus.AVAILABLE));
        equipment.add(new LabEquipment("EQ-03", "Centrifuge High-Speed", "Biochemical", "Lab Room 2C",
                EquipmentStatus.NEEDS_MAINTENANCE));
    }

    public String generateMemberId() {
        return "MEM-" + (memberIdCounter++);
    }

    public String generateCheckId() {
        return "CHK-" + (checkIdCounter++);
    }

    public void addMember(LabMember member) {
        if (member != null) {
            members.add(member);
        }
    }

    public List<LabMember> getAllMembers() {
        return members;
    }

    public List<LabEquipment> getAllEquipment() {
        return equipment;
    }

    public void recordSafetyCheck(SafetyCheckRecord record) {
        if (record != null) {
            checkRecords.add(record);
            if (!record.getViolations().isEmpty()) {
                violations.addAll(record.getViolations());
            }
        }
    }

    public List<SafetyCheckRecord> getAllCheckRecords() {
        return checkRecords;
    }

    public List<ViolationRecord> getAllViolations() {
        return violations;
    }
}

// =============================================================================
// PUBLIC MAIN CLASS (ENTRY POINT)
// =============================================================================

public class Main {
    private static final LabSafetyManager systemManager = new LabSafetyManager();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println("      WELCOME TO LAB SAFETY GUARD SYSTEM (JAVA OOP)        ");
        System.out.println("   Institutional Laboratory Safety, PPE & Equipment Audit   ");
        System.out.println("============================================================");

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = readIntegerInput("Enter your choice (1-8): ", 1, 8);

            switch (choice) {
                case 1:
                    registerLabMember();
                    break;
                case 2:
                    viewLabMembers();
                    break;
                case 3:
                    performSafetyCheck();
                    break;
                case 4:
                    viewSafetyViolations();
                    break;
                case 5:
                    viewLabEquipment();
                    break;
                case 6:
                    updateEquipmentStatus();
                    break;
                case 7:
                    generateSafetyReport();
                    break;
                case 8:
                    System.out.println("\nThank you for using Lab Safety Guard. Keep safety first in your laboratory!");
                    System.out.println("Exiting system safely...");
                    running = false;
                    break;
                default:
                    System.out.println("[Notice] Invalid selection. Please choose an option between 1 and 8.");
            }

            if (running) {
                promptEnterToContinue();
            }
        }
    }

    private static void displayMenu() {
        System.out.println();
        System.out.println("========== LAB SAFETY GUARD ==========");
        System.out.println("1. Register Lab Member");
        System.out.println("2. View Lab Members");
        System.out.println("3. Perform Lab Safety Check");
        System.out.println("4. View Safety Violations");
        System.out.println("5. View Lab Equipment");
        System.out.println("6. Update Equipment Status");
        System.out.println("7. Generate Safety Report");
        System.out.println("8. Exit");
        System.out.println("======================================");
    }

    private static void registerLabMember() {
        System.out.println("\n--- [1] REGISTER NEW LAB MEMBER ---");
        System.out.println("Select Member Role:");
        System.out.println("  1. Student");
        System.out.println("  2. Lab Assistant");
        int roleChoice = readIntegerInput("Choose role (1-2): ", 1, 2);

        String name = readNonEmptyString("Enter Full Name: ");
        String department = readNonEmptyString("Enter Department (e.g. Chemistry, Biotech): ");
        String contact = readNonEmptyString("Enter Contact Number: ");

        String memberId = systemManager.generateMemberId();

        if (roleChoice == 1) {
            String rollNo = readNonEmptyString("Enter Student Roll / Registration No: ");
            int semester = readIntegerInput("Enter Current Semester (1-8): ", 1, 8);
            String program = readNonEmptyString("Enter Academic Program (e.g., B.Sc, B.Tech, M.Sc): ");

            Student newStudent = new Student(memberId, name, department, contact, rollNo, semester, program);
            systemManager.addMember(newStudent);

            System.out.println("\n[Success] Student successfully registered!");
            System.out.println("Assigned Member ID: " + memberId);
            System.out.println("Clearance Level    : " + newStudent.getClearanceLevel());
        } else {
            String badgeId = readNonEmptyString("Enter Staff Badge ID: ");
            String labName = readNonEmptyString("Enter Assigned Laboratory Name: ");
            String shift = readNonEmptyString("Enter Work Shift (e.g., Morning, Evening, General): ");

            LabAssistant newAssistant = new LabAssistant(memberId, name, department, contact, badgeId, labName, shift);
            systemManager.addMember(newAssistant);

            System.out.println("\n[Success] Lab Assistant successfully registered!");
            System.out.println("Assigned Member ID: " + memberId);
            System.out.println("Clearance Level    : " + newAssistant.getClearanceLevel());
        }
    }

    private static void viewLabMembers() {
        System.out.println("\n--- [2] REGISTERED LAB MEMBERS ---");
        List<LabMember> members = systemManager.getAllMembers();

        if (members.isEmpty()) {
            System.out.println("[Notice] No lab members currently registered.");
            return;
        }

        System.out.println(
                "----------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-8s | %-18s | %-13s | %-10s | %-7s | %-15s | %-15s |\n",
                "ID", "NAME", "ROLE", "DEPT", "EXTRA 1", "EXTRA 2", "EXTRA 3");
        System.out.println(
                "----------------------------------------------------------------------------------------------------------------");

        for (LabMember m : members) {
            m.displayDetails();
        }
        System.out.println(
                "----------------------------------------------------------------------------------------------------------------");
        System.out.println("Total Registered Members: " + members.size());
    }

    private static void performSafetyCheck() {
        System.out.println("\n--- [3] PERFORM LAB SAFETY CHECK ---");
        List<LabMember> members = systemManager.getAllMembers();

        if (members.isEmpty()) {
            System.out.println("[Notice] Cannot perform check: No lab members registered in system.");
            return;
        }

        System.out.println("Select a lab member to inspect:");
        for (int i = 0; i < members.size(); i++) {
            LabMember m = members.get(i);
            System.out.printf("  %d. %-8s - %-18s [%s]\n", (i + 1), m.getMemberId(), m.getName(), m.getRole());
        }

        int memberIndex = readIntegerInput("Select member number (1-" + members.size() + "): ", 1, members.size()) - 1;
        LabMember targetMember = members.get(memberIndex);

        System.out.println(
                "\nStarting Safety Audit for: " + targetMember.getName() + " (" + targetMember.getMemberId() + ")");
        System.out.println("Please inspect the member and answer the following checklist questions (Y/N):\n");

        boolean labCoat = readYesNo("1. Is the member wearing a clean, fastened lab coat? (Y/N): ");
        boolean goggles = readYesNo("2. Are eye-protection safety goggles worn and fitted properly? (Y/N): ");
        boolean gloves = readYesNo("3. Are appropriate chemical/biological gloves worn? (Y/N): ");
        boolean cleanArea = readYesNo("4. Is the allocated workbench clean, tidy, and free of food/clutter? (Y/N): ");

        System.out.print("Enter any specific observations/notes (or press Enter to skip): ");
        String notes = scanner.nextLine().trim();

        SafetyChecklist checklist = new SafetyChecklist(labCoat, goggles, gloves, cleanArea, notes);
        String checkId = systemManager.generateCheckId();

        System.out.println("\n[Polymorphism Dispatch] Calling targetMember.performSafetyCheck(...)");
        SafetyCheckRecord checkResult = targetMember.performSafetyCheck(checklist, checkId);

        systemManager.recordSafetyCheck(checkResult);
        checkResult.displaySummary();
    }

    private static void viewSafetyViolations() {
        System.out.println("\n--- [4] SAFETY VIOLATION RECORDS ---");
        List<ViolationRecord> violations = systemManager.getAllViolations();

        if (violations.isEmpty()) {
            System.out.println("[Good News] No safety violations have been recorded so far! All audits passed.");
            return;
        }

        System.out.println("Total Violations Detected: " + violations.size() + "\n");
        for (int i = 0; i < violations.size(); i++) {
            System.out.println("Violation #" + (i + 1) + ":");
            violations.get(i).displayViolationDetails();
            System.out.println("------------------------------------------------------------");
        }
    }

    private static void viewLabEquipment() {
        System.out.println("\n--- [5] LAB EQUIPMENT INVENTORY ---");
        List<LabEquipment> equipment = systemManager.getAllEquipment();

        if (equipment.isEmpty()) {
            System.out.println("[Notice] No equipment items registered in the inventory.");
            return;
        }

        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("| %-8s | %-24s | %-16s | %-12s | %-18s |\n",
                "EQ ID", "EQUIPMENT NAME", "CATEGORY", "LOCATION", "STATUS");
        System.out.println("-----------------------------------------------------------------------------------------");

        for (LabEquipment eq : equipment) {
            eq.displayEquipmentDetails();
        }
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("Total Equipment Registered: " + equipment.size());
    }

    private static void updateEquipmentStatus() {
        System.out.println("\n--- [6] UPDATE EQUIPMENT STATUS ---");
        List<LabEquipment> equipment = systemManager.getAllEquipment();

        if (equipment.isEmpty()) {
            System.out.println("[Notice] No equipment available to update.");
            return;
        }

        System.out.println("Select an equipment item to update:");
        for (int i = 0; i < equipment.size(); i++) {
            LabEquipment eq = equipment.get(i);
            System.out.printf("  %d. %-8s - %-26s [Current: %s]\n", (i + 1), eq.getEquipmentId(), eq.getName(),
                    eq.getStatus());
        }

        int eqIndex = readIntegerInput("Select equipment number (1-" + equipment.size() + "): ", 1, equipment.size())
                - 1;
        LabEquipment selectedEquipment = equipment.get(eqIndex);

        System.out.println("\nUpdating Status for: " + selectedEquipment.getName() + " ("
                + selectedEquipment.getEquipmentId() + ")");
        System.out.println("Select New Status:");
        System.out.println("  1. AVAILABLE");
        System.out.println("  2. IN_USE");
        System.out.println("  3. NEEDS_MAINTENANCE");

        int statusChoice = readIntegerInput("Choose new status (1-3): ", 1, 3);
        EquipmentStatus newStatus;
        switch (statusChoice) {
            case 1:
                newStatus = EquipmentStatus.AVAILABLE;
                break;
            case 2:
                newStatus = EquipmentStatus.IN_USE;
                break;
            case 3:
                newStatus = EquipmentStatus.NEEDS_MAINTENANCE;
                break;
            default:
                newStatus = EquipmentStatus.AVAILABLE;
        }

        System.out.print("Enter reason or maintenance note: ");
        String note = scanner.nextLine().trim();

        boolean success = selectedEquipment.updateStatus(newStatus, note);
        if (success) {
            System.out.println("\n[Success] Status for " + selectedEquipment.getName() + " updated to: " + newStatus);
            System.out.println("Updated Note: " + selectedEquipment.getLastMaintenanceNote());
        } else {
            System.out.println("\n[Error] Failed to update equipment status.");
        }
    }

    private static void generateSafetyReport() {
        System.out.println("\n============================================================");
        System.out.println("                   COMPREHENSIVE LAB SAFETY REPORT           ");
        System.out.println("============================================================");

        List<SafetyCheckRecord> checks = systemManager.getAllCheckRecords();
        List<ViolationRecord> violations = systemManager.getAllViolations();
        List<LabEquipment> equipment = systemManager.getAllEquipment();
        List<LabMember> members = systemManager.getAllMembers();

        int totalChecks = checks.size();
        int passedChecks = 0;
        int failedChecks = 0;

        int highRiskCount = 0;
        int mediumRiskCount = 0;
        int lowRiskCount = 0;

        for (SafetyCheckRecord r : checks) {
            if (r.isPassed()) {
                passedChecks++;
            } else {
                failedChecks++;
            }
        }

        for (ViolationRecord v : violations) {
            if (v.getRiskLevel() == RiskLevel.HIGH) {
                highRiskCount++;
            } else if (v.getRiskLevel() == RiskLevel.MEDIUM) {
                mediumRiskCount++;
            } else if (v.getRiskLevel() == RiskLevel.LOW) {
                lowRiskCount++;
            }
        }

        double passRate = (totalChecks > 0) ? ((double) passedChecks / totalChecks) * 100.0 : 0.0;

        System.out.println("LAB OCCUPANCY & ASSETS SUMMARY:");
        System.out.println("  * Total Registered Members  : " + members.size());
        System.out.println("  * Total Tracked Equipment   : " + equipment.size());
        System.out.println();
        System.out.println("SAFETY INSPECTION METRICS:");
        System.out.println("  * Total Audits Conducted    : " + totalChecks);
        System.out.println("  * Checks Passed (Safe)      : " + passedChecks);
        System.out.println("  * Checks Failed (Violations): " + failedChecks);
        System.out.printf("  * Safety Pass Rate          : %.1f%%\n", passRate);
        System.out.println();
        System.out.println("VIOLATION RISK BREAKDOWN:");
        System.out.println("  * Total Violations Detected : " + violations.size());
        System.out.println("    - High Risk (Critical)    : " + highRiskCount);
        System.out.println("    - Medium Risk (Moderate)  : " + mediumRiskCount);
        System.out.println("    - Low Risk (Minor)        : " + lowRiskCount);
        System.out.println();

        int equipAvailable = 0;
        int equipInUse = 0;
        int equipMaint = 0;
        for (LabEquipment eq : equipment) {
            if (eq.getStatus() == EquipmentStatus.AVAILABLE)
                equipAvailable++;
            else if (eq.getStatus() == EquipmentStatus.IN_USE)
                equipInUse++;
            else if (eq.getStatus() == EquipmentStatus.NEEDS_MAINTENANCE)
                equipMaint++;
        }
        System.out.println("EQUIPMENT READINESS STATUS:");
        System.out.println("  * Available for Use         : " + equipAvailable);
        System.out.println("  * Currently In Use          : " + equipInUse);
        System.out.println("  * Needs Maintenance/Repair  : " + equipMaint);

        System.out.println("\nRECENT SAFETY AUDIT LOGS:");
        if (checks.isEmpty()) {
            System.out.println("  [No safety checks recorded yet. Perform a check using Menu Option 3.]");
        } else {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.printf("| %-8s | %-16s | %-16s | %-10s | %-8s | %-12s |\n",
                    "CHECK ID", "TIMESTAMP", "MEMBER NAME", "ROLE", "STATUS", "MAX RISK");
            System.out.println("-----------------------------------------------------------------------------------");
            for (SafetyCheckRecord r : checks) {
                String status = r.isPassed() ? "PASSED" : "FAILED";
                String risk = r.isPassed() ? "NONE" : r.getRiskLevel().toString();
                System.out.printf("| %-8s | %-16s | %-16s | %-10s | %-8s | %-12s |\n",
                        r.getCheckId(), r.getTimestamp().format(dtf), r.getMemberName(), r.getMemberRole(), status,
                        risk);
            }
            System.out.println("-----------------------------------------------------------------------------------");
        }
        System.out.println("============================================================\n");
    }

    private static int readIntegerInput(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String raw = scanner.nextLine().trim();
            if (raw.isEmpty()) {
                System.out.println("[Warning] Input cannot be blank. Please enter a valid number.");
                continue;
            }
            try {
                int value = Integer.parseInt(raw);
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println(
                            "[Warning] Value out of range. Please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("[Warning] Invalid number format. Please enter numerical digits only.");
            }
        }
    }

    private static String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("[Warning] Field cannot be empty. Please enter required information.");
        }
    }

    private static boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes") || input.equals("true") || input.equals("1")) {
                return true;
            } else if (input.equals("n") || input.equals("no") || input.equals("false") || input.equals("0")) {
                return false;
            }
            System.out.println("[Warning] Please answer with 'Y' for Yes or 'N' for No.");
        }
    }

    private static void promptEnterToContinue() {
        System.out.print("\nPress [ENTER] to return to the Main Menu...");
        scanner.nextLine();
    }
}