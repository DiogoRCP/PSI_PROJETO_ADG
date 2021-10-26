<?php

namespace frontend\models;

use Yii;

/**
 * This is the model class for table "cars".
 *
 * @property int $id
 * @property string $vin
 * @property string $registration
 * @property string $purschasedate
 * @property int $kilometers
 * @property string $state
 * @property int $userId
 *
 * @property Repairs[] $repairs
 * @property Users $user
 */
class Cars extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'cars';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['vin', 'registration', 'purschasedate', 'kilometers', 'state', 'userId'], 'required'],
            [['purschasedate'], 'safe'],
            [['kilometers', 'userId'], 'integer'],
            [['vin', 'registration', 'state'], 'string', 'max' => 100],
            [['vin'], 'unique'],
            [['userId'], 'exist', 'skipOnError' => true, 'targetClass' => Users::className(), 'targetAttribute' => ['userId' => 'id']],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'vin' => 'Vin',
            'registration' => 'Registration',
            'purschasedate' => 'Purschasedate',
            'kilometers' => 'Kilometers',
            'state' => 'State',
            'userId' => 'User ID',
        ];
    }

    /**
     * Gets query for [[Repairs]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getRepairs()
    {
        return $this->hasMany(Repairs::className(), ['carId' => 'id']);
    }

    /**
     * Gets query for [[User]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getUser()
    {
        return $this->hasOne(Users::className(), ['id' => 'userId']);
    }
}
