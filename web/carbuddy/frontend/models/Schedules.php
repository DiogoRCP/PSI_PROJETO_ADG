<?php

namespace frontend\models;

use Yii;

/**
 * This is the model class for table "schedules".
 *
 * @property int $id
 * @property string|null $currentdate
 * @property string $schedulingdate
 * @property string $repairdescription
 * @property string $state
 * @property string $repairtype
 * @property int $carId
 * @property int $companyId
 *
 * @property Cars $car
 * @property Companies $company
 */
class Schedules extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'schedules';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['currentdate', 'schedulingdate'], 'safe'],
            [['schedulingdate', 'repairdescription', 'state', 'repairtype', 'carId', 'companyId'], 'required'],
            [['carId', 'companyId'], 'integer'],
            [['repairdescription', 'state', 'repairtype'], 'string', 'max' => 100],
            [['carId'], 'exist', 'skipOnError' => true, 'targetClass' => Cars::className(), 'targetAttribute' => ['carId' => 'id']],
            [['companyId'], 'exist', 'skipOnError' => true, 'targetClass' => Companies::className(), 'targetAttribute' => ['companyId' => 'id']],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'currentdate' => 'Registry Date',
            'schedulingdate' => 'Scheduling Date',
            'repairdescription' => 'Repair Description',
            'state' => 'State',
            'repairtype' => 'Repair Type',
            'carId' => 'Car',
            'companyId' => 'Company',
        ];
    }

    /**
     * Gets query for [[Car]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getCar()
    {
        return $this->hasOne(Cars::className(), ['id' => 'carId']);
    }

    /**
     * Gets query for [[Company]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getCompany()
    {
        return $this->hasOne(Companies::className(), ['id' => 'companyId']);
    }
}
