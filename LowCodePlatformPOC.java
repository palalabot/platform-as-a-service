import java.util.*;
import java.time.LocalDateTime;

/**
 * LOW-CODE PLATFORM SERVICE
 * Proof of Concept Only
 *
 * Demonstrates:
 * 1. Authentication
 * 2. Email Notifications
 * 3. Workflow Logic
 *
 * All operations are simulated.
 * This is NOT a production implementation.
 */

public class LowCodePlatformPOC {

    // ==========================================
    // 1. AUTHENTICATION SERVICE
    // ==========================================

    static class AuthService {

        private Map<String, String> users = new HashMap<>();

        public void register(String username, String password) {
            users.put(username, password);
            System.out.println("[AUTH] User registered: " + username);
        }

        public boolean login(String username, String password) {

            if (users.containsKey(username)
                    && users.get(username).equals(password)) {

                System.out.println("[AUTH] Login successful: " + username);
                return true;
            }

            System.out.println("[AUTH] Login failed: " + username);
            return false;
        }

        public void logout(String username) {
            System.out.println("[AUTH] User logged out: " + username);
        }
    }


    // ==========================================
    // 2. EMAIL NOTIFICATION SERVICE
    // ==========================================

    static class EmailNotificationService {

        public void sendEmail(
                String recipient,
                String subject,
                String message) {

            // Simulated email delivery
            System.out.println("\n[EMAIL NOTIFICATION]");
            System.out.println("To: " + recipient);
            System.out.println("Subject: " + subject);
            System.out.println("Message: " + message);
            System.out.println("Status: Simulated delivery");
        }
    }


    // ==========================================
    // 3. WORKFLOW ENGINE
    // ==========================================

    static class WorkflowEngine {

        public void executeWorkflow(
                String workflowName,
                String initiatedBy,
                String requestType) {

            System.out.println("\n[WORKFLOW ENGINE]");
            System.out.println("Workflow: " + workflowName);
            System.out.println("Initiated by: " + initiatedBy);

            // Step 1: Receive request
            System.out.println("Step 1: Request received.");

            // Step 2: Evaluate business logic
            if (requestType.equalsIgnoreCase("APPROVAL")) {

                System.out.println("Step 2: Approval required.");

                // Step 3: Simulate approval
                System.out.println("Step 3: Request approved.");

            } else if (requestType.equalsIgnoreCase("AUTOMATED")) {

                System.out.println("Step 2: No approval required.");
                System.out.println("Step 3: Automated processing.");

            } else {

                System.out.println("Step 2: Invalid workflow type.");
                return;
            }

            // Step 4: Complete workflow
            System.out.println("Step 4: Workflow completed.");
        }
    }


    // ==========================================
    // 4. LOW-CODE PLATFORM ORCHESTRATOR
    // ==========================================

    static class LowCodePlatform {

        AuthService auth = new AuthService();
        EmailNotificationService email =
                new EmailNotificationService();
        WorkflowEngine workflow = new WorkflowEngine();

        public void startPlatform() {

            System.out.println("================================");
            System.out.println("   LOW-CODE PLATFORM SERVICE");
            System.out.println("================================");

            // Register sample user
            auth.register("admin", "demo123");

            // Authenticate user
            boolean authenticated =
                    auth.login("admin", "demo123");

            if (!authenticated) {
                return;
            }

            // Trigger workflow
            workflow.executeWorkflow(
                    "Employee Leave Request",
                    "admin",
                    "APPROVAL"
            );

            // Send notification
            email.sendEmail(
                    "employee@example.com",
                    "Leave Request Update",
                    "Your leave request has been approved."
            );

            // End session
            auth.logout("admin");

            System.out.println("\nPlatform demonstration complete.");
        }
    }


    // ==========================================
    // 5. APPLICATION ENTRY POINT
    // ==========================================

    public static void main(String[] args) {

        LowCodePlatform platform = new LowCodePlatform();

        platform.startPlatform();
    }
}import java.util.*;
import java.time.LocalDateTime;

/**
 * LOW-CODE PLATFORM SERVICE
 * Proof of Concept Only
 *
 * Demonstrates:
 * 1. Authentication
 * 2. Email Notifications
 * 3. Workflow Logic
 *
 * All operations are simulated.
 * This is NOT a production implementation.
 */

public class LowCodePlatformPOC {

    // ==========================================
    // 1. AUTHENTICATION SERVICE
    // ==========================================

    static class AuthService {

        private Map<String, String> users = new HashMap<>();

        public void register(String username, String password) {
            users.put(username, password);
            System.out.println("[AUTH] User registered: " + username);
        }

        public boolean login(String username, String password) {

            if (users.containsKey(username)
                    && users.get(username).equals(password)) {

                System.out.println("[AUTH] Login successful: " + username);
                return true;
            }

            System.out.println("[AUTH] Login failed: " + username);
            return false;
        }

        public void logout(String username) {
            System.out.println("[AUTH] User logged out: " + username);
        }
    }


    // ==========================================
    // 2. EMAIL NOTIFICATION SERVICE
    // ==========================================

    static class EmailNotificationService {

        public void sendEmail(
                String recipient,
                String subject,
                String message) {

            // Simulated email delivery
            System.out.println("\n[EMAIL NOTIFICATION]");
            System.out.println("To: " + recipient);
            System.out.println("Subject: " + subject);
            System.out.println("Message: " + message);
            System.out.println("Status: Simulated delivery");
        }
    }


    // ==========================================
    // 3. WORKFLOW ENGINE
    // ==========================================

    static class WorkflowEngine {

        public void executeWorkflow(
                String workflowName,
                String initiatedBy,
                String requestType) {

            System.out.println("\n[WORKFLOW ENGINE]");
            System.out.println("Workflow: " + workflowName);
            System.out.println("Initiated by: " + initiatedBy);

            // Step 1: Receive request
            System.out.println("Step 1: Request received.");

            // Step 2: Evaluate business logic
            if (requestType.equalsIgnoreCase("APPROVAL")) {

                System.out.println("Step 2: Approval required.");

                // Step 3: Simulate approval
                System.out.println("Step 3: Request approved.");

            } else if (requestType.equalsIgnoreCase("AUTOMATED")) {

                System.out.println("Step 2: No approval required.");
                System.out.println("Step 3: Automated processing.");

            } else {

                System.out.println("Step 2: Invalid workflow type.");
                return;
            }

            // Step 4: Complete workflow
            System.out.println("Step 4: Workflow completed.");
        }
    }


    // ==========================================
    // 4. LOW-CODE PLATFORM ORCHESTRATOR
    // ==========================================

    static class LowCodePlatform {

        AuthService auth = new AuthService();
        EmailNotificationService email =
                new EmailNotificationService();
        WorkflowEngine workflow = new WorkflowEngine();

        public void startPlatform() {

            System.out.println("================================");
            System.out.println("   LOW-CODE PLATFORM SERVICE");
            System.out.println("================================");

            // Register sample user
            auth.register("admin", "demo123");

            // Authenticate user
            boolean authenticated =
                    auth.login("admin", "demo123");

            if (!authenticated) {
                return;
            }

            // Trigger workflow
            workflow.executeWorkflow(
                    "Employee Leave Request",
                    "admin",
                    "APPROVAL"
            );

            // Send notification
            email.sendEmail(
                    "employee@example.com",
                    "Leave Request Update",
                    "Your leave request has been approved."
            );

            // End session
            auth.logout("admin");

            System.out.println("\nPlatform demonstration complete.");
        }
    }


    // ==========================================
    // 5. APPLICATION ENTRY POINT
    // ==========================================

    public static void main(String[] args) {

        LowCodePlatform platform = new LowCodePlatform();

        platform.startPlatform();
    }
}
