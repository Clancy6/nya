import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.HeadMethod;

public class MainActivity extends AppCompatActivity {
    private EditText editTextServerAddress, editTextUsername, editTextPassword;
    private Button buttonTestConnection;
    private TextView textViewConnectionResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextServerAddress = findViewById(R.id.editTextServerAddress);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonTestConnection = findViewById(R.id.buttonTestConnection);
        textViewConnectionResult = findViewById(R.id.textViewConnectionResult);

        buttonTestConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testConnection();
            }
        });
    }

    private void testConnection() {
        String serverAddress = editTextServerAddress.getText().toString();
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        HttpClient client = new HttpClient();
        client.getState().setCredentials(
                new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
                new UsernamePasswordCredentials(username, password)
        );

        HeadMethod method = new HeadMethod(serverAddress);

        try {
            int statusCode = client.executeMethod(method);
            if (statusCode == 200) {
                textViewConnectionResult.setText("连接成功");
            } else {
                textViewConnectionResult.setText("连接失败，错误代码：" + statusCode);
            }
        } catch (Exception e) {
            textViewConnectionResult.setText("连接失败，错误信息：" + e.getMessage());
        } finally {
            method.releaseConnection();
        }
    }
}
