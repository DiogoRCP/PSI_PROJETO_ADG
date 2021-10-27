<?php

namespace frontend\models;

use yii\base\Model;
use yii\data\ActiveDataProvider;
use frontend\models\Cars;

/**
 * CarSearch represents the model behind the search form of `frontend\models\Cars`.
 */
class CarSearch extends Cars
{
    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['id', 'displacement', 'kilometers', 'userId'], 'integer'],
            [['vin', 'brand', 'model', 'color', 'carType', 'fuelType', 'registration', 'purschasedate', 'state'], 'safe'],
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
        $query = Cars::find();

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
            'displacement' => $this->displacement,
            'purschasedate' => $this->purschasedate,
            'kilometers' => $this->kilometers,
            'userId' => $this->userId,
        ]);

        $query->andFilterWhere(['like', 'vin', $this->vin])
            ->andFilterWhere(['like', 'brand', $this->brand])
            ->andFilterWhere(['like', 'model', $this->model])
            ->andFilterWhere(['like', 'color', $this->color])
            ->andFilterWhere(['like', 'carType', $this->carType])
            ->andFilterWhere(['like', 'fuelType', $this->fuelType])
            ->andFilterWhere(['like', 'registration', $this->registration])
            ->andFilterWhere(['like', 'state', $this->state]);

        return $dataProvider;
    }
}
