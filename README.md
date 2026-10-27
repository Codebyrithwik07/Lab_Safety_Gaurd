# Lab Safety Guard

A console-based Java application for managing laboratory safety. It registers lab members (students and lab assistants), runs PPE and workstation safety checks, records violations with risk levels, tracks lab equipment, and generates a summary safety report.

Built as an Object-Oriented Programming (OOP) micro-project. The whole program is in a single file, `Main.java`.

---

## Requirements

- Java Development Kit (JDK) 8 or newer
- A terminal (PowerShell, Command Prompt, macOS/Linux terminal) or any Java IDE

Check your installation:

```
java -version
javac -version
```

---

## How to Run

1. Put `Main.java` in a folder and open a terminal in that folder.
2. Compile:

   ```
   javac Main.java
   ```

3. Run:

   ```
   java Main
   ```

Java 11 or newer can also run it directly without compiling: `java Main.java`

> **Note:** the file must be named exactly `Main.java`, because the public class is `Main`.

---

## Using the Program

When it starts, the main menu appears:

```
========== LAB SAFETY GUARD ==========
1. Register Lab Member
2. View Lab Members
3. Perform Lab Safety Check
4. View Safety Violations
5. View Lab Equipment
6. Update Equipment Status
7. Generate Safety Report
8. Exit
======================================
```

Type the option number and press **Enter**. After each action, press **Enter** to return to the menu.

### 1. Register Lab Member

Choose a role, then answer the prompts.

| Role | Details asked |
|---|---|
| Student | Name, department, contact number, roll number, semester (1-8), program |
| Lab Assistant | Name, department, contact number, badge ID, lab name, work shift |

A Member ID (`MEM-101`, `MEM-102`, ...) is assigned automatically.

### 2. View Lab Members
Shows all registered members in a table.

### 3. Perform Lab Safety Check
1. Select a registered member by number.
2. Answer four Yes/No questions: lab coat, safety goggles, gloves, clean workbench.
3. Optionally enter observation notes.

The result (PASSED or FAILED, highest risk level, violations found) is shown and saved. A Check ID (`CHK-5001`, ...) is assigned automatically.

**Risk levels differ by role:**

| Missing item | Student | Lab Assistant |
|---|---|---|
| Lab coat | MEDIUM | HIGH |
| Safety goggles | HIGH | HIGH |
| Gloves | MEDIUM | MEDIUM |
| Untidy workbench | LOW | MEDIUM |

### 4. View Safety Violations
Lists every violation recorded so far, with the check ID, member name, type, risk level and description.

### 5. View Lab Equipment
Shows the equipment inventory and each item's status. Three sample items are pre-loaded.

### 6. Update Equipment Status
Select an item, choose a new status (`AVAILABLE`, `IN_USE` or `NEEDS_MAINTENANCE`), and optionally add a note. Press Enter to skip the note and keep the old one.

### 7. Generate Safety Report
Shows a summary: members and equipment count, audits passed/failed, pass rate, violations by risk level, equipment readiness, and a log of all safety checks.

### 8. Exit
Closes the program.

---

## Input Rules

| Field | Rule |
|---|---|
| Menu choices | A whole number within the range shown |
| Contact number | Exactly 10 digits |
| Roll number / badge ID | Must be unique (not case sensitive) |
| Semester | 1 to 8 |
| Text fields | Cannot be left blank |
| Yes/No questions | `y`, `yes`, `1` or `n`, `no`, `0` (not case sensitive) |

Invalid input shows a `[Warning]` message and asks again.

---

## Example Session

```
Enter your choice (1-8): 1
Choose role (1-2): 1
Enter Full Name: Asha Rao
Enter Department (e.g. Chemistry, Biotech): Chemistry
Enter Contact Number (10 digits): 9876543210
Enter Student Roll / Registration No: R001
Enter Current Semester (1-8): 3
Enter Academic Program (e.g., B.Sc, B.Tech, M.Sc): B.Tech

[Success] Student successfully registered!
Assigned Member ID: MEM-101
Clearance Level    : Level 1 (Standard Student Access)
```

---

## Project Structure and OOP Concepts

All classes are in `Main.java`.

| Class / Type | Purpose |
|---|---|
| `LabMember` (abstract) | Base class for all members |
| `Student`, `LabAssistant` | Subclasses with their own safety rules |
| `SafetyChecklist` | Holds the answers from one inspection |
| `SafetyCheckRecord` | Result of one inspection |
| `ViolationRecord` | One recorded violation |
| `LabEquipment` | One equipment item |
| `LabSafetyManager` | Stores and manages all data |
| `EquipmentStatus`, `RiskLevel` | Enums |
| `Fmt` | Helper that keeps table columns aligned |
| `Main` | Menu and user interaction |

OOP concepts used: **inheritance** (`Student` and `LabAssistant` extend `LabMember`), **abstraction** (abstract methods in `LabMember`), **polymorphism** (`performSafetyCheck()` behaves differently for each role), **encapsulation** (private fields with getters), and **enums**.

---

## Limitations

- Data is stored in memory only. Everything is lost when the program closes.
- There is no login or user accounts.
- Equipment cannot be added or removed from the menu. The three starting items are set in code.

---

## Troubleshooting

| Problem | Fix |
|---|---|
| `Could not find or load main class Main` | Compile first with `javac Main.java`, and run from the folder that contains the `.class` files |
| `'javac' is not recognized` | Install a JDK and add its `bin` folder to your PATH |
| `UnsupportedClassVersionError ... class file version 61.0` | `javac` and `java` are different versions. Compile with `javac --release 8 Main.java`, or make both point to the same JDK |
| Table columns look misaligned | Use a monospace font in your terminal |
