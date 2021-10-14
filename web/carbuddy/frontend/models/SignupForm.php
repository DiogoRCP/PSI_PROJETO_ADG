<?php

namespace frontend\models;

use Yii;
use yii\base\Model;
use common\models\User;

/**
 * Signup form
 */
class SignupForm extends Model
{
    public $username;
    public $email;
    public $userpassword;
    public $nif;
    public $birsthday;
    public $phonenumber;
    public $usertype = "pendente";

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        //definição das regras de inserção de dados
        //email, username e nif como unicos
        //todos os campos de preenchimento obrigatorio
        return [
            ['username', 'trim'],
            ['username', 'required'],
            ['username', 'unique', 'targetClass' => '\common\models\User', 'message' => 'This username has already been taken.'],
            ['username', 'string', 'min' => 2, 'max' => 255],

            ['email', 'trim'],
            ['email', 'required'],
            ['email', 'email'],
            ['email', 'string', 'max' => 255],
            ['email', 'unique', 'targetClass' => '\common\models\User', 'message' => 'This email address has already been taken.'],

            ['userpassword', 'required'],
            ['userpassword', 'string', 'min' => Yii::$app->params['user.passwordMinLength']],

            ['nif', 'required'],
            ['nif', 'unique', 'targetClass' => '\common\models\User', 'message' => 'This NIF has already been taken.'],

            ['birsthday', 'required'],

            ['phonenumber', 'required']
        ];
    }

    /**
     * Signs user up.
     *
     * @return bool whether the creating new account was successful and email was sent
     */
    public function signup()
    {
        if (!$this->validate()) {
            return null;
        }
        
        $user = new User();
        $user->username = $this->username;
        $user->email = $this->email;
        $user->usertype = $this->usertype;
        $user->password = $this->userpassword;
        $user->phonenumber = $this->phonenumber;
        $user->birsthday = $this->birsthday;
        $user->userpassword = $this->userpassword;
        $user->nif = $this->nif;
        $user->setPassword($this->userpassword);
        $user->generateAuthKey();
        $user->generateEmailVerificationToken();

        return $user->save() && $this->sendEmail($user);
    }

    /**
     * Sends confirmation email to user
     * @param User $user user model to with email should be send
     * @return bool whether the email was sent
     */
    protected function sendEmail($user)
    {
        return Yii::$app
            ->mailer
            ->compose(
                ['html' => 'emailVerify-html', 'text' => 'emailVerify-text'],
                ['user' => $user]
            )
            ->setFrom([Yii::$app->params['supportEmail'] => Yii::$app->name . ' robot'])
            ->setTo($this->email)
            ->setSubject('Account registration at ' . Yii::$app->name)
            ->send();
    }
}
