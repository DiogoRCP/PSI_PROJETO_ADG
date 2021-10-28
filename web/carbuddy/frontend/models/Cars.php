<?php

namespace frontend\models;

use Yii;

/**
 * This is the model class for table "cars".
 *
 * @property int $id
 * @property string $vin
 * @property string $brand
 * @property string $model
 * @property string $color
 * @property string $carType
 * @property float $displacement
 * @property string $fuelType
 * @property string $registration
 * @property string $modelyear
 * @property int $kilometers
 * @property string $state
 * @property int $userId
 *
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
            [['vin', 'brand', 'model', 'color', 'carType', 'displacement', 'fuelType', 'registration', 'modelyear', 'kilometers', 'state', 'userId'], 'required'],
            [['displacement'], 'number'],
            [['modelyear'], 'safe'],
            [['kilometers', 'userId'], 'integer'],
            [['vin', 'brand', 'model', 'color', 'carType', 'fuelType', 'registration', 'state'], 'string', 'max' => 100],
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
            'vin' => 'VIN',
            'brand' => 'Brand',
            'model' => 'Model',
            'color' => 'Color',
            'carType' => 'Car Type',
            'displacement' => 'Displacement',
            'fuelType' => 'Fuel Type',
            'registration' => 'Registration',
            'modelyear' => 'Model year',
            'kilometers' => 'Kilometers',
            'state' => 'State',
            'userId' => 'User ID',
        ];
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
