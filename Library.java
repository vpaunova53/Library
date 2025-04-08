import java.util.*;
public class Library {

    class Book {
        private String title;
        private String author;
        private boolean isBorrowed;

        public Book(String title, String author) {
            this.title = title;
            this.author = author;
            this.isBorrowed = false;
        }

        public String getTitle() {
            return title;
        }

        public boolean isAvailable() {
            return !isBorrowed;
        }

        public void borrow() {
            isBorrowed = true;
        }

        public void returnBook() {
            isBorrowed = false;
        }

        public void displayInfo() {
            System.out.println("\"" + title + "\" by " + author + " [" + (isBorrowed ? "Borrowed" : "Available") + "]");
        }
    }

    public class LibrarySystem {
        private static final Scanner scanner = new Scanner(System.in);
        private static final List<Book> books = new ArrayList<>();

        public static void main(String[] args) {
            int choice;

            do {
                showMenu();
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1 -> addBook();
                    case 2 -> listBooks();
                    case 3 -> searchBook();
                    case 4 -> borrowBook();
                    case 5 -> returnBook();
                    case 6 -> System.out.println("Exiting system. Goodbye!");
                    default -> System.out.println("Invalid option. Try again.");
                }
            } while (choice != 6);
        }

        private static void showMenu() {
            System.out.println("\n--- Library Management System ---");
            System.out.println("1. Add Book");
            System.out.println("2. List All Books");
            System.out.println("3. Search Book by Title");
            System.out.println("4. Borrow a Book");
            System.out.println("5. Return a Book");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
        }

        private static void addBook() {
            System.out.print("Enter book title: ");
            String title = scanner.nextLine();

            System.out.print("Enter author: ");
            String author = scanner.nextLine();

            books.add(new Book(title, author));
            System.out.println("Book added successfully!");
        }

        private static void listBooks() {
            System.out.println("\n--- Book List ---");
            if (books.isEmpty()) {
                System.out.println("No books available.");
            } else {
                for (Book b : books) {
                    b.displayInfo();
                }
            }
        }

        private static void searchBook() {
            System.out.print("Enter title to search: ");
            String title = scanner.nextLine().toLowerCase();
            boolean found = false;

            for (Book b : books) {
                if (b.getTitle().toLowerCase().contains(title)) {
                    b.displayInfo();
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No matching book found.");
            }
        }

        private static void borrowBook() {
            System.out.print("Enter title to borrow: ");
            String title = scanner.nextLine().toLowerCase();

            for (Book b : books) {
                if (b.getTitle().toLowerCase().equals(title)) {
                    if (b.isAvailable()) {
                        b.borrow();
                        System.out.println("Book borrowed successfully!");
                    } else {
                        System.out.println("Book is already borrowed.");
                    }
                    return;
                }
            }

            System.out.println("Book not found.");
        }

        private static void returnBook() {
            System.out.print("Enter title to return: ");
            String title = scanner.nextLine().toLowerCase();

            for (Book b : books) {
                if (b.getTitle().toLowerCase().equals(title)) {
                    if (!b.isAvailable()) {
                        b.returnBook();
                        System.out.println("Book returned successfully!");
                    } else {
                        System.out.println("Book is not currently borrowed.");
                    }
                    return;
                }
            }

            System.out.println("Book not found.");
        }
    }

}
