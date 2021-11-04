<?php

namespace frontend\models;

use Symfony\Component\Yaml\Dumper;
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
    public $password;
    public $nif;
    public $birsthday;
    public $phonenumber;
    public $usertype = "client";

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

            ['password', 'required'],
            ['password', 'string', 'min' => Yii::$app->params['user.passwordMinLength']],

            ['nif', 'required'],
            ['nif', 'string', 'max' => 9],
            ['nif', 'string', 'min' => 9],
            ['nif', 'unique', 'targetClass' => '\common\models\User', 'message' => 'This NIF has already been taken.'],

            ['birsthday', 'required'],

            ['phonenumber', 'required'],
            ['phonenumber', 'string', 'max' => 9],
            ['phonenumber', 'string', 'min' => 9]
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
        $user->phonenumber = $this->phonenumber;
        $user->birsthday = $this->birsthday;
        $user->nif = $this->nif;
        $user->setPassword($this->password);
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
