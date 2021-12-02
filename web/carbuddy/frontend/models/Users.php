<?php

namespace frontend\models;

use Yii;

/**
 * This is the model class for table "user".
 *
 * @property int $id
 * @property string $username
 * @property string $password_hash
 * @property string $verification_token
 * @property string|null $password_reset_token
 * @property string $auth_key
 * @property int $status
 * @property int $updated_at
 * @property int $created_at
 * @property string $usertype
 * @property string $nif
 * @property string $birsthday
 * @property string $email
 * @property string $phonenumber
 * @property string|null $registrationdate
 *
 * @property Cars[] $cars
 * @property Contributors $contributors
 */
class Users extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'user';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['username', 'password_hash', 'verification_token', 'auth_key', 'updated_at', 'created_at', 'usertype', 'nif', 'birsthday', 'email', 'phonenumber'], 'required'],
            [['verification_token'], 'string'],
            [['status', 'updated_at', 'created_at'], 'integer'],
            [['birsthday', 'registrationdate'], 'safe'],
            [['username', 'password_hash', 'password_reset_token', 'email'], 'string', 'max' => 255],
            [['auth_key'], 'string', 'max' => 32],
            [['usertype'], 'string', 'max' => 100],
            [['nif'], 'string', 'max' => 9],
            [['phonenumber'], 'string', 'max' => 40],
            [['nif'], 'unique'],
            [['username'], 'unique'],
            [['email'], 'unique'],
            [['password_reset_token'], 'unique'],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'username' => 'Username',
            'password_hash' => 'Password',
            'verification_token' => 'Verification Token',
            'auth_key' => 'Auth Key',
            'status' => 'Status',
            'updated_at' => 'Updated At',
            'created_at' => 'Created At',
            'usertype' => 'User Type',
            'nif' => 'NIF',
            'birsthday' => 'Birthday',
            'email' => 'E-Mail',
            'phonenumber' => 'Phone Number',
            'registrationdate' => 'Registration Date',
        ];
    }

    /**
     * Gets query for [[Cars]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getCars()
    {
        return $this->hasMany(Cars::className(), ['userId' => 'id']);
    }

    /**
     * Gets query for [[Contributors]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getContributors()
    {
        return $this->hasOne(Contributors::className(), ['userId' => 'id']);
    }

    public function newPassword($password)
    {
        if($this->password_hash!=$password){
            $this->password_hash=Yii::$app->security->generatePasswordHash($this->password_hash);
        }

        return true;
    }

    //Funções e metodos dos testes
    public function setusername($username)
    {
        $this->username=$username;
    }

    public function setpasswordhash($passwordhash)
    {
        $this->password_hash=$passwordhash;
    }

    public function setverificationtoken($verificationtoken)
    {
        $this->verification_token=$verificationtoken;
    }

    public function setpasswordresettoken($passwordresettoken)
    {
        $this->password_reset_token=$passwordresettoken;
    }

    public function setauthkey($authkey)
    {
        $this->auth_key=$authkey;
    }

    public function setstatus($status)
    {
        $this->status=$status;
    }

    public function setupdatedat($updatedat)
    {
        $this->updated_at=$updatedat;
    }

    public function setcreatedat($createdat)
    {
        $this->created_at=$createdat;
    }

    public function setusertype($usertype)
    {
        $this->usertype=$usertype;
    }

    public function setnif($nif)
    {
        $this->nif=$nif;
    }

    public function setbirsthday($birsthday)
    {
        $this->birsthday=$birsthday;
    }

    public function setemail($email)
    {
        $this->email=$email;
    }

    public function setphonenumber($phonenumber)
    {
        $this->phonenumber=$phonenumber;
    }

    public function setregistrationdate($registrationdate)
    {
        $this->registrationdate=$registrationdate;
    }
}
