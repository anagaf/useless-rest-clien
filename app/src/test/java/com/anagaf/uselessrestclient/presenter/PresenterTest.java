package com.anagaf.uselessrestclient.presenter;

import com.anagaf.uselessrestclient.TestUtils;
import com.anagaf.uselessrestclient.model.User;
import com.anagaf.uselessrestclient.service.AbstractJsonPlaceholderService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PresenterTest {
    private final MockWebServer webServer = new MockWebServer();

    private Presenter presenter;

    private Presenter.View view;

    private CountDownLatch countDownLatch;

    @Before
    public void setUp() throws Exception {
        webServer.start();

        view = mock(Presenter.View.class);
        final Answer countDownAnswer = new Answer() {
            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                countDownLatch.countDown();
                return null;
            }
        };
        doAnswer(countDownAnswer).when(view).showError(anyString());
        doAnswer(countDownAnswer).when(view).showUsers(any(List.class));

        final TestService service = new TestService(webServer.url("").toString());

        presenter = new DefaultPresenter(service);
        presenter.start(view);
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

        verify(view).showProgressBar();
        final ArgumentCaptor<List> usersArg = ArgumentCaptor.forClass(List.class);
        verify(view).showUsers(usersArg.capture());
        final List<User> users = usersArg.getValue();
        assertEquals(10, users.size());
        assertEquals("Leanne Graham", users.get(0).getName());
        assertEquals("Rey.Padberg@karina.biz", users.get(9).getEmail());
    }

    @Test
    public void shouldReportHttpErrorToListener() throws InterruptedException {
        webServer.enqueue(new MockResponse().setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR));

        countDownLatch = new CountDownLatch(1);

        presenter.retrieveUsers();

        countDownLatch.await(3, TimeUnit.SECONDS);

        verify(view).showProgressBar();
        verify(view).showError(anyString());
    }

    @Test
    public void shouldReportConnectionErrorToListener() throws InterruptedException, IOException {
        webServer.shutdown();

        countDownLatch = new CountDownLatch(1);

        presenter.retrieveUsers();

        countDownLatch.await(3, TimeUnit.SECONDS);

        verify(view).showProgressBar();
        verify(view).showError(anyString());
    }

    /* ========== Inner Classes ========== */

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
