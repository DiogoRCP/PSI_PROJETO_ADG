<?php

namespace frontend\models;

use yii\base\Model;
use yii\data\ActiveDataProvider;
use frontend\models\Schedules;

/**
 * SchedulesSearch represents the model behind the search form of `frontend\models\Schedules`.
 */
class SchedulesSearch extends Schedules
{
    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['id', 'carId', 'companyId'], 'integer'],
            [['currentdate', 'schedulingdate', 'repairdescription', 'state', 'repairtype'], 'safe'],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function scenarios()
    {
        // bypass scenarios() implementation in the parent class
        return Model::scenarios();
    }

    /**
     * Creates data provider instance with search query applied
     *
     * @param array $params
     *
     * @return ActiveDataProvider
     */
    public function search($params)
    {
        $query = Schedules::find();

        // add conditions that should always apply here

        $dataProvider = new ActiveDataProvider([
            'query' => $query,
        ]);

        $this->load($params);

        if (!$this->validate()) {
            // uncomment the following line if you do not want to return any records when validation fails
            // $query->where('0=1');
            return $dataProvider;
        }

        // grid filtering conditions
        $query->andFilterWhere([
            'id' => $this->id,
            'currentdate' => $this->currentdate,
            'schedulingdate' => $this->schedulingdate,
            'carId' => $this->carId,
            'companyId' => $this->companyId,
        ]);

        $query->andFilterWhere(['like', 'repairdescription', $this->repairdescription])
            ->andFilterWhere(['like', 'state', $this->state])
            ->andFilterWhere(['like', 'repairtype', $this->repairtype]);

        return $dataProvider;
    }
}
