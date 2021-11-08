<?php

namespace app\models;

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
            'companyname' => 'Companyname',
            'nif' => 'Nif',
            'email' => 'Email',
            'phonenumber' => 'Phonenumber',
            'registrationdate' => 'Registrationdate',
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
}
