<?php

namespace backend\models;

use Yii;

/**
 * This is the model class for table "contributors".
 *
 * @property int $id
 * @property string $speciality
 * @property int $companyId
 * @property int $userId
 *
 * @property Companies $company
 * @property Repairs[] $repairs
 * @property Users $user
 */
class Contributors extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'contributors';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['speciality', 'companyId', 'userId'], 'required'],
            [['companyId', 'userId'], 'integer'],
            [['speciality'], 'string', 'max' => 100],
            [['userId'], 'unique'],
            [['companyId'], 'exist', 'skipOnError' => true, 'targetClass' => Companies::className(), 'targetAttribute' => ['companyId' => 'id']],
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
            'speciality' => 'Speciality',
            'companyId' => 'Company',
            'userId' => 'User',
        ];
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

    /**
     * Gets query for [[Repairs]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getRepairs()
    {
        return $this->hasMany(Repairs::className(), ['contributorId' => 'id']);
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
