<?php

namespace frontend\models;

use Yii;
use yii\helpers\VarDumper;

/**
 * This is the model class for table "repairs".
 *
 * @property int $id
 * @property int $kilometers
 * @property string|null $repairdate
 * @property string $repairdescription
 * @property string $state
 * @property string $repairtype
 * @property int $carId
 * @property int $contributorId
 *
 * @property Cars $car
 * @property Contributors $contributor
 */
class Repairs extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'repairs';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['kilometers', 'repairdescription', 'state', 'repairtype', 'carId', 'contributorId'], 'required'],
            [['kilometers', 'carId', 'contributorId'], 'integer'],
            [['repairdate'], 'safe'],
            [['repairdescription', 'state', 'repairtype'], 'string', 'max' => 100],
            [['carId'], 'exist', 'skipOnError' => true, 'targetClass' => Cars::className(), 'targetAttribute' => ['carId' => 'id']],
            [['contributorId'], 'exist', 'skipOnError' => true, 'targetClass' => Contributors::className(), 'targetAttribute' => ['contributorId' => 'id']],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'kilometers' => 'Kilometers',
            'repairdescription' => 'Repair Description',
            'state' => 'State',
            'repairtype' => 'Repair Type',
            'carId' => 'Car',
            'contributorId' => 'Contributor',
            'repairdate' => 'Repair Start Date',
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
     * Gets query for [[Contributor]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getContributor()
    {
        return $this->hasOne(Contributors::className(), ['id' => 'contributorId']);
    }

    //Funções e metodos dos testes
    public function setkilometers($kilometers)
    {
        $this->kilometers = $kilometers;
    }

    public function setrepairdate($repairdate)
    {
        $this->repairdate = $repairdate;
    }

    public function setrepairdescription($repairdescription)
    {
        $this->repairdescription = $repairdescription;
    }

    public function setstate($state)
    {
        $this->state = $state;
    }

    public function setrepairstype($repairtype)
    {
        $this->repairtype = $repairtype;
    }

    public function setcarid($carid)
    {
        $this->carId = $carid;
    }

    public function setcontributorid($contributorid)
    {
        $this->contributorId = $contributorid;
    }
}
