import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.TreeSet;

public class SocialMediaPlatform {

    public static void main(String[] args) {
        SocialMediaPlatform platform = new SocialMediaPlatform();
        platform.runPlatform();
    }

    public void runPlatform() {
        // Kullanıcılar oluşturuluyor
        User ali = new User("Ali");
        User hilal = new User("Hilal");
        User trks = new User("Trks");

        // Kullanıcılar birbirini takip ediyor
        ali.follow(hilal);
        hilal.follow(trks);

        // Kullanıcılar gönderi oluşturuyor
        ali.createPost("Merhaba dünya!");
        hilal.createPost("Java öğreniyorum!");

        // Kullanıcılar gönderilere yorum yapıyor
        ali.addCommentToPost(hilal, 0, "Harika bir gönderi!");
        hilal.addCommentToPost(ali, 0, "Teşekkürler!");

        // Kullanıcılar gönderileri beğeniyor
        ali.addToPostFavorites(hilal, 0);
        hilal.addToPostFavorites(ali, 0);

        // Kullanıcıların akışını göster
        ali.showFeed();
    }

    class User {
        private String name;
        private LinkedHashMap<Integer, Post> posts; // Kullanıcının gönderileri
        private HashSet<User> following; // Takip edilen kullanıcılar
        private TreeSet<Post> favorites; // Beğenilen Gönderiler
        private int postCounter = 0; // Gönderi Sayacı

        public User(String name) {
            this.name = name;
            this.posts = new LinkedHashMap<>();
            this.following = new HashSet<>();
            this.favorites = new TreeSet<>();
        }

        public String getName() {
            return name;
        }

        public void follow(User user) {
            following.add(user);
            System.out.println(name + ", " + user.getName() + " kullanıcısını takip ediyor.");
        }

        public void createPost(String content) {
            Post newPost = new Post(postCounter++, this, content);
            posts.put(newPost.getId(), newPost);
            System.out.println(name + " yeni bir gönderi yayınladı: " + content);
        }

        public void addCommentToPost(User user, int postId, String comment) {
            Post post = user.getPost(postId);
            if (post != null) {
                post.addComment(new Comment(this, comment));
                System.out.println(name + ", " + user.getName() + "'in gönderisine yorum yaptı: " + comment);
            }
        }

        public void addToPostFavorites(User user, int postId) {
            Post post = user.getPost(postId);
            if (post != null) {
                favorites.add(post);
                System.out.println(name + ", " + user.getName() + "'in gönderisini beğendi: " + post.getContent());
            }
        }

        public void showFeed() {
            System.out.println("\n" + name + "'in Ana Sayfası");
            for (User user : following) {
                user.showPosts();
            }
        }

        public Post getPost(int id) {
            return posts.get(id);
        }

        public void showPosts() {
            for (Post post : posts.values()) {
                System.out.println(post.getContent());
                for (Comment comment : post.getComments()) {
                    System.out.println("  Yorum: " + comment.getText());
                }
            }
        }
    }

    class Post implements Comparable<Post> {
        private int id;
        private User author;
        private String content;
        private LinkedHashMap<Integer, Comment> comments; // Gönderinin yorumları

        public Post(int id, User author, String content) {
            this.id = id;
            this.author = author;
            this.content = content;
            this.comments = new LinkedHashMap<>();
        }

        public int getId() {
            return id;
        }

        public User getAuthor() {
            return author;
        }

        public String getContent() {
            return content;
        }

        public void addComment(Comment comment) {
            comments.put(comments.size(), comment);
        }

        public Iterable<Comment> getComments() {
            return comments.values();
        }

        @Override
        public int compareTo(Post other) {
            return Integer.compare(this.id, other.id);
        }
    }

    class Comment {
        private User author;
        private String text;

        public Comment(User author, String text) {
            this.author = author;
            this.text = text;
        }

        public User getAuthor() {
            return author;
        }

        public String getText() {
            return text;
        }
    }
}

