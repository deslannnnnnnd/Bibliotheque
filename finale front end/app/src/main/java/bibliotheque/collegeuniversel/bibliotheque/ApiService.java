package bibliotheque.collegeuniversel.bibliotheque;

import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {
    // ========== BOOKS ==========
    @GET("api/books")
    Call<List<Book>> getAllBooks();
    
    @GET("api/books/search")
    Call<List<Book>> searchBooks(@Query("query") String query);
    
    @GET("api/books/{id}")
    Call<Book> getBookById(@Path("id") Long id);
    
    // ========== LOANS (EMPRUNTS) ==========
    @GET("api/loans")
    Call<List<Loan>> getAllLoans();
    
    @GET("api/loans/user/{userId}")
    Call<List<Loan>> getLoansByUser(@Path("userId") Long userId);
    
    @GET("api/loans/statut/{statut}")
    Call<List<Loan>> getLoansByStatut(@Path("statut") String statut);
    
    @GET("api/loans/retard")
    Call<List<Loan>> getLoansEnRetard();
    
    @POST("api/loans")
    Call<Loan> emprunterLivre(@Body Map<String, Long> request);
    
    @PUT("api/loans/{id}/extend")
    Call<Loan> prolongerEmprunt(@Path("id") Long id);
    
    @PUT("api/loans/{id}/return")
    Call<Loan> retournerLivre(@Path("id") Long id);
    
    // ========== FINES (AMENDES) ==========
    @GET("api/fines/user/{userId}")
    Call<List<Fine>> getFinesByUser(@Path("userId") Long userId);
    
    @GET("api/fines/user/{userId}/impayees")
    Call<List<Fine>> getFinesImpayeesByUser(@Path("userId") Long userId);
    
    @GET("api/fines/impayees")
    Call<List<Fine>> getAllFinesImpayees();
    
    @POST("api/fines/{id}/pay")
    Call<Fine> payerAmende(@Path("id") Long id);
    
    @POST("api/fines/{id}/cancel")
    Call<Fine> annulerAmende(@Path("id") Long id);
    
    // ========== NOTIFICATIONS ==========
    @GET("api/notifications/user/{userId}")
    Call<List<Notification>> getNotificationsByUser(@Path("userId") Long userId);
    
    @GET("api/notifications/user/{userId}/unread")
    Call<List<Notification>> getNotificationsNonLues(@Path("userId") Long userId);
    
    @GET("api/notifications/user/{userId}/count-unread")
    Call<Map<String, Long>> countNotificationsNonLues(@Path("userId") Long userId);
    
    @PUT("api/notifications/{id}/mark-read")
    Call<Notification> marquerNotificationLue(@Path("id") Long id);
    
    @PUT("api/notifications/user/{userId}/mark-all-read")
    Call<Map<String, String>> marquerToutesNotificationsLues(@Path("userId") Long userId);
}