<?php

namespace frontend\models;

use backend\models\Contributors;
use Yii;
use yii\base\Model;
use yii\data\ActiveDataProvider;
use frontend\models\Repairs;
use yii\helpers\VarDumper;

/**
 * RepairsSearch represents the model behind the search form of `frontend\models\Repairs`.
 */
class RepairsSearch extends Repairs
{
    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['id', 'kilometers', 'carId', 'contributorId'], 'integer'],
            [['repairdate', 'repairdescription', 'state', 'repairtype'], 'safe'],
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
        $query = Repairs::find();

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

        $user = Users::findOne(Yii::$app->user->getId());

        $collaborator = Contributors::find()
            ->where(['userId' => $user->id])
            ->one();

        // grid filtering conditions
        $query->andFilterWhere([
            'id' => $this->id,
            'kilometers' => $this->kilometers,
            'repairdate' => $this->repairdate,
        ]);

        if(Yii::$app->request->get('car')){
            $query->andFilterWhere([
                'carId' => Yii::$app->request->get('car'),
            ]);
        }
        else{
            try {
                $query->andFilterWhere([
                    'contributorId' =>$collaborator->id,
                ]);
            } catch(\yii\db\IntegrityException $e) {
                $query->andFilterWhere([
                    'id' =>'0',
                ]);
                Yii::$app->session->setFlash('error', 'You must have an associated company');
            }
        }

        $query->andFilterWhere(['like', 'repairdescription', $this->repairdescription])
            ->andFilterWhere(['like', 'state', $this->state])
            ->andFilterWhere(['like', 'repairtype', $this->repairtype]);

        return $dataProvider;
    }
}
