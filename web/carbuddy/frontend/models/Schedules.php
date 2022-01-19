<?php

namespace frontend\models;

use common\mosquitto\phpMQTT;
use Yii;
use yii\helpers\VarDumper;

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


    //Funções e metodos dos testes
    public function setcurrentdate($currentdate)
    {
        $this->currentdate=$currentdate;
    }

    public function setschedulingdate($schedulingdate)
    {
        $this->schedulingdate=$schedulingdate;
    }

    public function setrepairdescription($repairdescription)
    {
        $this->repairdescription=$repairdescription;
    }

    public function setstate($state)
    {
        $this->state=$state;
    }

    public function setrepairstype($repairtype)
    {
        $this->repairtype=$repairtype;
    }

    public function setcarid($carid)
    {
        $this->carId=$carid;
    }

    public function setcompanyid($companyid)
    {
        $this->companyId=$companyid;
    }

    //Mosquitto
    public function afterSave($insert, $changedAttributes)
    {
        if(Yii::$app->user->can('collaborator')) {
            parent::afterSave($insert, $changedAttributes);

            $message = $this->car->id . ":::" . $this->car->brand . " " . $this->car->model . ":::is " . $this->state . ":::" . $this->schedulingdate;

            $this->FazPublish("SCHEDULE-" . $this->car->userId, $message);
        }
    }

    public function FazPublish($canal, $msg)
    {
        $server = "127.0.0.1";
        $port = 1883;
        $username = ""; // set your username
        $password = ""; // set your password
        $client_id = "phpMQTT-publisher"; // unique!
        $mqtt = new phpMQTT($server, $port, $client_id);
        if ($mqtt->connect(true, NULL, $username, $password)) {
            $mqtt->publish($canal, $msg, 0, true);
            $mqtt->close();
        } else {
            file_put_contents('debug.output', 'Time out!');
        }
    }
}