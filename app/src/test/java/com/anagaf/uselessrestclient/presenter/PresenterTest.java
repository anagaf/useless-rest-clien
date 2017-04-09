package com.anagaf.uselessrestclient.presenter;

import com.anagaf.uselessrestclient.TestUtils;
import com.anagaf.uselessrestclient.model.User;
import com.anagaf.uselessrestclient.service.AbstractJsonPlaceholderService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class PresenterTest {
    private final MockWebServer webServer = new MockWebServer();

    private Presenter presenter;

    private TestView listener;

    private CountDownLatch countDownLatch;

    @Before
    public void setUp() throws Exception {
        webServer.start();

        listener = new TestView();

        final TestService service = new TestService(webServer.url("").toString());

        presenter = new DefaultPresenter(service);
        presenter.start(listener);
    }

    @After
    public void tearDown() throws Exception {
        webServer.shutdown();
        presenter.stop();
    }

    @Test
    public void shouldTransferRetrievedUsersToListener() throws IOException, InterruptedException {
        final String response = TestUtils.getResourceText("users.json");
        webServer.enqueue(new MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
                                  .setBody(response));

        countDownLatch = new CountDownLatch(1);

        presenter.retrieveUsers();

        countDownLatch.await(3, TimeUnit.SECONDS);

        assertEquals(10, listener.users.size());
        assertEquals("Leanne Graham", listener.users.get(0).getName());
        assertEquals("Rey.Padberg@karina.biz", listener.users.get(9).getEmail());
    }

    @Test
    public void shouldReportHttpErrorToListener() throws InterruptedException {
        webServer.enqueue(new MockResponse().setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR));

        countDownLatch = new CountDownLatch(1);

        presenter.retrieveUsers();

        countDownLatch.await(3, TimeUnit.SECONDS);

        assertNotNull(listener.errorMessage);
    }

    @Test
    public void shouldReportConnectionErrorToListener() throws InterruptedException, IOException {
        webServer.shutdown();

        countDownLatch = new CountDownLatch(1);

        presenter.retrieveUsers();

        countDownLatch.await(3, TimeUnit.SECONDS);

        assertNotNull(listener.errorMessage);
    }

    /* ========== Inner Classes ========== */

    private final class TestView implements Presenter.View {
        List<User> users;
        String errorMessage;

        @Override
        public void showProgressBar() {

        }

        @Override
        public void showUsers(final List<User> users) {
            this.users = users;
            countDownLatch.countDown();
        }

        @Override
        public void showError(final String message) {
            this.errorMessage = message;
            countDownLatch.countDown();
        }
    }

    private final static class TestService extends AbstractJsonPlaceholderService {

        private final String host;

        TestService(final String hostName) {
            this.host = hostName;
        }

        @Override
        protected String getHost() {
            return this.host;
        }
    }
}
