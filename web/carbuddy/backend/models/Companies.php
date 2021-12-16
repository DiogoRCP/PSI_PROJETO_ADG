<?php

namespace backend\models;

use Yii;

/**
 * This is the model class for table "companies".
 *
 * @property int $id
 * @property string $companyname
 * @property string $nif
 * @property string $email
 * @property string $phonenumber
 * @property string|null $registrationdate
 *
 * @property Contributors[] $contributors
 */
class Companies extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'companies';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['companyname', 'nif', 'email', 'phonenumber'], 'required'],
            [['registrationdate'], 'safe'],
            [['companyname', 'email'], 'string', 'max' => 100],
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
            'companyname' => 'Company Name',
            'nif' => 'NIF',
            'email' => 'E-mail',
            'phonenumber' => 'Phone Number',
            'registrationdate' => 'Registration Date',
        ];
    }

    /**
     * Gets query for [[Contributors]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getContributors()
    {
        return $this->hasMany(Contributors::className(), ['companyId' => 'id']);
    }

    public function getRepair(){
        $reparacoesTotal = [];
        $contributor = $this->getContributors()->all();
        foreach($contributor as $each){
            $reparacoes = $each->getRepairs()->all();
            foreach ($reparacoes as $rep){
                $reparacoesTotal[] = $rep;
            }
        }
        return $reparacoesTotal;
    }

    public function getRepairCount(){
        $reparacoesTotal = [];
        $contributor = $this->getContributors()->all();
        foreach($contributor as $each){
            $reparacoes = $each->getRepairs()->all();
            foreach ($reparacoes as $rep){
                $reparacoesTotal[] = $rep;
            }
        }
        return sizeof($reparacoesTotal);
    }

    //Funções e metodos dos testes
    public function setname($name)
    {
        $this->companyname=$name;
    }

    public function setnif($nif)
    {
        $this->nif=$nif;
    }

    public function setemail($email)
    {
        $this->email=$email;
    }

    public function setphonenumber($phonenumber)
    {
        $this->phonenumber=$phonenumber;
    }

    public function setreistrationdate($date)
    {
        $this->registrationdate=$date;
    }

}
