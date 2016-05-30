package com.hubel.thu.thelab.ui.fragment;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.TransitionManager;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hubel.thu.thelab.R;
import com.hubel.thu.thelab.ui.activity.MainActivity;
import com.hubel.thu.thelab.ui.base.BaseFragment;
import com.hubel.thu.thelab.ui.util.DisplayUtil;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 *
 */
public class SignInFragment extends BaseFragment {

    private static final String TAG = "SignInFragment";

    private static final int PERMISSIONS_REQUEST_GET_ACCOUNTS = 0;

    // region Views
    @BindView(R.id.container)
    ViewGroup container;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.email_til)
    TextInputLayout emailInputLayout;
    @BindView(R.id.password_til)
    TextInputLayout passwordInputLayout;
    @BindView(R.id.password_et)
    EditText passwordEditText;
    @BindView(R.id.email_et)
    AutoCompleteTextView emailEditText;
    @BindView(R.id.sign_in_ll)
    LinearLayout signInLinearLayout;
    @BindView(R.id.sign_up_ll)
    LinearLayout signUpLinearLayout;
    @BindView(R.id.sign_up_btn)
    Button signUpButton;
    @BindView(R.id.sign_in_btn)
    Button signInButton;
    //Android M Bug : dont show material like progressbar
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.forgot_password_btn)
    Button forgotPasswordButton;
    @BindView(R.id.permission_primer)
    CheckBox permissionPrimer;

    private boolean shouldPromptForPermission = false;

    // endregion

    // region Member Variables
    private Pattern pattern = android.util.Patterns.EMAIL_ADDRESS;
    private Matcher matcher;
    // endregion

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    // region Constructors
    public SignInFragment() {
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
    }
    // endregion

    // region Factory Methods
    public static SignInFragment newInstance(Bundle extras) {
        SignInFragment fragment = new SignInFragment();
        fragment.setArguments(extras);
        return fragment;
    }

    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    // endregion

    // region Lifecycle Methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // Retain this fragment across configuration changes.
        setRetainInstance(true);


        setHasOptionsMenu(true);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    startMainActivity();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    private void startMainActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_in, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setupAccountAutocomplete();
        finishSetup();


        loading.setVisibility(View.GONE);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle(getString(R.string.sign_in));
        }

        Observable<CharSequence> emailChangeObservable = RxTextView.textChanges(emailEditText);
        Observable<CharSequence> passwordChangeObservable = RxTextView.textChanges(passwordEditText);

        // Checks for validity of the email input field

        Subscription emailSubscription = emailChangeObservable
                .doOnNext(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence charSequence) {
                        hideEmailError();
                    }
                })
                .debounce(400, TimeUnit.MILLISECONDS)
                .filter(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        return !TextUtils.isEmpty(charSequence);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()) // UI Thread
                .subscribe(new Subscriber<CharSequence>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(CharSequence charSequence) {
                        boolean isEmailValid = validateEmail(charSequence.toString());
                        if (!isEmailValid) {
                            showEmailError();
                        } else {
                            hideEmailError();
                        }
                    }
                });

        compositeSubscription.add(emailSubscription);

        // Checks for validity of the password input field

        Subscription passwordSubscription = passwordChangeObservable
                .doOnNext(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence charSequence) {
                        hidePasswordError();
                    }
                })
                .debounce(400, TimeUnit.MILLISECONDS)
                .filter(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        return !TextUtils.isEmpty(charSequence);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()) // UI Thread
                .subscribe(new Subscriber<CharSequence>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(CharSequence charSequence) {
                        boolean isPasswordValid = validatePassword(charSequence.toString());
                        if (!isPasswordValid) {
                            showPasswordError();
                        } else {
                            hidePasswordError();
                        }
                    }
                });

        compositeSubscription.add(passwordSubscription);

        // Checks for validity of all input fields

        Subscription signInFieldsSubscription = Observable.combineLatest(emailChangeObservable, passwordChangeObservable, new Func2<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence email, CharSequence password) {
                boolean isEmailValid = validateEmail(email.toString());
                boolean isPasswordValid = validatePassword(password.toString());

                return isEmailValid && isPasswordValid;
            }
        }).observeOn(AndroidSchedulers.mainThread()) // UI Thread
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Boolean validFields) {
                        if (validFields) {
                            enableSignIn();
                        } else {
                            disableSignIn();
                        }
                    }
                });

        compositeSubscription.add(signInFieldsSubscription);
    }


    // endregion

    // region Helper Methods
    private void enableError(TextInputLayout textInputLayout) {
        if (textInputLayout.getChildCount() == 2)
            textInputLayout.getChildAt(1).setVisibility(View.VISIBLE);
    }

    private void disableError(TextInputLayout textInputLayout) {
        if (textInputLayout.getChildCount() == 2)
            textInputLayout.getChildAt(1).setVisibility(View.GONE);
    }

    private boolean validateEmail(String email) {
        if (TextUtils.isEmpty(email))
            return false;

        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean validatePassword(String password) {
        return password.length() > 5;
    }

    private void showEmailError() {
        enableError(emailInputLayout);
        // emailInputLayout.setErrorEnabled(true);
        emailInputLayout.setError(getString(R.string.invalid_email));
    }

    private void hideEmailError() {
        disableError(emailInputLayout);
        // emailInputLayout.setErrorEnabled(false);
        emailInputLayout.setError(null);
    }

    private void showPasswordError() {
        enableError(passwordInputLayout);
        // passwordInputLayout.setErrorEnabled(true);
        passwordInputLayout.setError(getString(R.string.invalid_password));
    }

    private void hidePasswordError() {
        disableError(passwordInputLayout);
        // passwordInputLayout.setErrorEnabled(false);
        passwordInputLayout.setError(null);
    }

    private void enableSignIn() {
        signInLinearLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        signInButton.setEnabled(true);
        signInButton.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
    }

    private void disableSignIn() {
        signInLinearLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.grey_400));
        signInButton.setEnabled(false);
        signInButton.setTextColor(ContextCompat.getColor(getContext(), R.color.grey_500));
    }


    // region Listeners
    @OnClick(R.id.sign_in_btn)
    public void onSignInButtonClicked(View view) {
        DisplayUtil.hideKeyboard(getContext(), view);
        signInUser();
    }

    private void signInUser() {
        showLoading();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startMainActivity();

                }else{
                    Log.w(TAG, "signInWithEmail", task.getException());
                    showLoginFailed();
                    Toast.makeText(getActivity(),task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



    // endregion

    // region Listeners

    @OnClick(R.id.sign_up_btn)
    public void onSignUpButton(View view) {
        DisplayUtil.hideKeyboard(getContext(), view);
        signUpUser();
    }

    private void signUpUser() {

        showLoading();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if(validateEmail(emailEditText.getText().toString()) && validatePassword(passwordEditText.getText().toString())){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                            if (task.isSuccessful()){
                                startMainActivity();
                            }else {
                                showLoginFailed();
                                Toast.makeText(getActivity(), task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        } else if (!validateEmail(emailEditText.getText().toString())){
            showEmailError();
        } else if(!validatePassword(passwordEditText.getText().toString())){
            showPasswordError();
        }
    }


    @OnClick(R.id.forgot_password_btn)
    public void onForgotPasswordButton(View view){
        DisplayUtil.hideKeyboard(getContext(), view);
        forgotPassword();
    }

    private void forgotPassword() {

        String emailAddress = emailEditText.getText().toString();

        if(validateEmail(emailAddress)){
            mAuth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Email sent.");

                            }else {
                                Toast.makeText(getActivity(), task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            showEmailError();
        }

    }


    private void showLoginFailed() {
        Snackbar.make(container, R.string.login_failed, Snackbar.LENGTH_LONG).show();
        showLogin();
        passwordEditText.requestFocus();
    }

    private void maybeShowAccounts() {
        if (emailEditText.hasFocus()
                && emailEditText.isAttachedToWindow()
                && emailEditText.getAdapter() != null
                && emailEditText.getAdapter().getCount() > 0) {
            emailEditText.showDropDown();
        }
    }

    private void showLogin() {
        TransitionManager.beginDelayedTransition(container);
        signUpLinearLayout.setVisibility(View.VISIBLE);
        forgotPasswordButton.setVisibility(View.VISIBLE);
        signUpButton.setVisibility(View.VISIBLE);
        emailInputLayout.setVisibility(View.VISIBLE);
        passwordInputLayout.setVisibility(View.VISIBLE);
        signInLinearLayout.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
    }

    private void showLoading() {
        TransitionManager.beginDelayedTransition(container);
        forgotPasswordButton.setVisibility(View.GONE);
        signUpLinearLayout.setVisibility(View.GONE);
        signUpButton.setVisibility(View.GONE);
        emailInputLayout.setVisibility(View.GONE);
        permissionPrimer.setVisibility(View.GONE);
        passwordInputLayout.setVisibility(View.GONE);
        signInLinearLayout.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
    }

    private void setupAccountAutocomplete() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.GET_ACCOUNTS) ==
                PackageManager.PERMISSION_GRANTED) {
            permissionPrimer.setVisibility(View.GONE);
            final Account[] accounts = AccountManager.get(getActivity()).getAccounts();
            final Set<String> emailSet = new HashSet<>();
            for (Account account : accounts) {
                if (Patterns.EMAIL_ADDRESS.matcher(account.name).matches()) {
                    emailSet.add(account.name);
                }
            }
            emailEditText.setAdapter(new ArrayAdapter<>(getActivity(),
                    R.layout.account_dropdown_item, new ArrayList<>(emailSet)));
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.GET_ACCOUNTS)) {
                setupPermissionPrimer();
            } else {
                permissionPrimer.setVisibility(View.GONE);
                shouldPromptForPermission = true;
            }
        }
    }

    private void setupPermissionPrimer() {
        permissionPrimer.setChecked(false);
        permissionPrimer.setVisibility(View.VISIBLE);
        permissionPrimer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    requestPermissions(new String[]{ Manifest.permission.GET_ACCOUNTS },
                            PERMISSIONS_REQUEST_GET_ACCOUNTS);
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_GET_ACCOUNTS) {
            TransitionManager.beginDelayedTransition(container);
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupAccountAutocomplete();
                emailEditText.requestFocus();
                emailEditText.showDropDown();
            } else {
                // if permission was denied check if we should ask again in the future (i.e. they
                // did not check 'never ask again')
                if (shouldShowRequestPermissionRationale(Manifest.permission.GET_ACCOUNTS)) {
                    setupPermissionPrimer();
                } else {
                    // denied & shouldn't ask again. deal with it (•_•) ( •_•)>⌐■-■ (⌐■_■)
                    TransitionManager.beginDelayedTransition(container);
                    permissionPrimer.setVisibility(View.GONE);
                }
            }
        }
    }

    /**
     * Show the permissions dialog or account dropdown
     * during the enter animation which is jarring.
     */
    private void finishSetup() {
        if (shouldPromptForPermission) {
            requestPermissions(new String[]{ Manifest.permission.GET_ACCOUNTS },
                    PERMISSIONS_REQUEST_GET_ACCOUNTS);
            shouldPromptForPermission = false;
        }
        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                maybeShowAccounts();
            }
        });
        maybeShowAccounts();
    }


    // endregion

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}

