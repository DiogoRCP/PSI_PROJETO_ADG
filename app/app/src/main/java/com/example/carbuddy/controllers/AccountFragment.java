package com.example.carbuddy.controllers;

import static com.example.carbuddy.utils.libs.SelectedMainMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.carbuddy.R;
import com.example.carbuddy.listeners.LoginListener;
import com.example.carbuddy.models.Login;
import com.example.carbuddy.models.ModeloBDHelper;
import com.example.carbuddy.singletons.LoginSingleton;

/** extends Fragment - herança de classe do Fragmento
 * implements LoginListener - implementação do Listener
 * */
public class AccountFragment extends Fragment implements LoginListener {

    /**
     * A simple {@link Fragment} subclass.
     * Use the {@link com.example.carbuddy.controllers.RepairFragment#newInstance} factory method to
     * create an instance of this fragment.
     */

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    /** Definição das variáveis globais*/
    private String mParam1;
    private String mParam2;
    private ModeloBDHelper database;
    private Login login;
    public TextView textViewAccUserName, textViewAccEmail, textViewNif, textViewBirthday, textViewPhoneNumber;

    /** Construtor do fragmento em vazio - requerido pelo programa*/
    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RepairFragment.
     */
    // TODO: Rename and change types and number of parameters

    /** Método de criação de uma nova instância do fragmento */
    public static com.example.carbuddy.controllers.AccountFragment newInstance(String param1, String param2) {
        com.example.carbuddy.controllers.AccountFragment fragment = new com.example.carbuddy.controllers.AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /** Função onCreate
     * - provém da extensão do Fragment
     * - Inicialização dos argumentos da instância criada anteriormente
     * - Conexão à base de dados
     * - carregarDados(): Inicializa a Singleton
     * - setHasOptionsMenu(true): Função necessária para conseguirmos ter acesso ao menu existente
     * */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        database = new ModeloBDHelper(getContext());

        carregarDados();

        setHasOptionsMenu(true);
    }

    /** Inicializa a Singleton */
    private void carregarDados() {

        //Define o fragmento onde é disparado o listener
        LoginSingleton.getInstance(getContext()).setLoginListenerAccount(this);

        //Carregar a Singleton com os Dados da API
        LoginSingleton.getInstance(getContext()).apiAccount(getContext());

        //Variavel fica inicialmente carregada com o Singleton
        login = LoginSingleton.getInstance(getContext()).getLogin();
    }

    /** Função necessária para conseguirmos ter acesso ao menu existente */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.account, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /** - Função que permite gerar uma ação ao clicar no item do menu
     *  - Identificação do item do menu
     *  - Criação de um novo intent para inicializar a ação do menu neste fragmento
     *  - Aceder ao activity signup para edição da conta
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bt_acc_edit:

                Intent signup = new Intent(getContext(), SignupActivity.class);
                signup.putExtra("userEdit", login);
                startActivity(signup);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Função onCreateView
     * - Gerar a view e tudo o que é visual
     * - Associar o layout FragmentAccount ao objeto view
     * - Definição de título
     * - Definição e chamada de textviews
     * - Definição e chamada de botão
     * */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle(getString(R.string.Account));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.Account);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(null);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View view = inflater.inflate(R.layout.fragment_account,
                container, false);

        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_garage, container, false);
        // Inflate the layout for this fragment

        textViewAccUserName = view.findViewById(R.id.textViewAccUserName);
        textViewAccEmail = view.findViewById(R.id.textViewAccEmail);
        textViewNif = view.findViewById(R.id.textViewNif);
        textViewBirthday = view.findViewById(R.id.textViewBirthday);
        textViewPhoneNumber = view.findViewById(R.id.textViewPhoneNumber);

        updateAccount();

        SelectedMainMenu(getActivity(), R.id.btMainAccount);

        return view;
    }

    /** Validação dos dados de conta */
    @Override
    public void onValidateLogin(Login login) {
        //Atualizar o objeto login com a API
        this.login = login;
        //Fazer o update à base de dados
        database.insertLogin(this.login);

        updateAccount();
    }

    /** Passa os dados do objeto login para o design */
    private void updateAccount() {
        textViewAccUserName.setText(login.getUsername());
        textViewAccEmail.setText(login.getEmail());
        textViewNif.setText(login.getNif());
        textViewBirthday.setText(login.getBirsthday());
        textViewPhoneNumber.setText(login.getPhonenumber());
    }

    /** Função onResume
     * - atualizar os dados quando a view entra no estado onResume
     * - quando alteramos um dado e retornamos para a página anterior (que estava on pause anteriormente)
     * irá fazer onResume e atualizar automaticamente os dados
     * */
    @Override
    public void onResume() {
        super.onResume();
        carregarDados();
    }
}

