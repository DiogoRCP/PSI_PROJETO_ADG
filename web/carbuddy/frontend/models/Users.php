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
            [['username', 'password_hash', 'verification_token', 'auth_key', 'status', 'updated_at', 'created_at', 'usertype', 'nif', 'birsthday', 'email', 'phonenumber'], 'required'],
            [['password_hash', 'verification_token', 'auth_key'], 'string'],
            [['status', 'updated_at', 'created_at'], 'integer'],
            [['birsthday', 'registrationdate'], 'safe'],
            [['username', 'usertype', 'email'], 'string', 'max' => 100],
            [['nif'], 'string', 'max' => 9],
            [['phonenumber'], 'string', 'max' => 40],
            [['nif'], 'unique'],
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
            'password_hash' => 'Password Hash',
            'verification_token' => 'Verification Token',
            'auth_key' => 'Auth Key',
            'status' => 'Status',
            'updated_at' => 'Updated At',
            'created_at' => 'Created At',
            'usertype' => 'Usertype',
            'nif' => 'Nif',
            'birsthday' => 'Birsthday',
            'email' => 'Email',
            'phonenumber' => 'Phonenumber',
            'registrationdate' => 'Registrationdate',
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
}
