<?php

namespace backend\models;

use Yii;
use yii\helpers\VarDumper;

/**
 * This is the model class for table "users".
 *
 * @property int $id
 * @property string $username
 * @property string $password
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
            [['username', 'usertype', 'nif', 'birsthday', 'email', 'phonenumber'], 'required'],
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
            'password' => 'password',
            'usertype' => 'User Type',
            'nif' => 'NIF',
            'birsthday' => 'Birthday',
            'email' => 'E-mail',
            'phonenumber' => 'Phone Number',
            'registrationdate' => 'Registration Date',
        ];
    }

    public function getUserTypes()
    {
        $query = $this::find()
            ->where(['usertype' => $this->usertype])
            ->count('usertype');
        return $query;
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
